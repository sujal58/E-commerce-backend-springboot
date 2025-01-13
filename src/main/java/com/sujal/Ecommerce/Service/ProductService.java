package com.sujal.Ecommerce.Service;


import com.sujal.Ecommerce.DTO.Request.CreateProductDto;
import com.sujal.Ecommerce.DTO.Request.UpdateProductDto;
import com.sujal.Ecommerce.DTO.Response.ProductResponseDto;
import com.sujal.Ecommerce.Entity.Category;
import com.sujal.Ecommerce.Entity.Product;
import com.sujal.Ecommerce.Entity.User;
import com.sujal.Ecommerce.Exceptions.ProductNotFoundException;
import com.sujal.Ecommerce.Repository.ProductRepository;
import com.sujal.Ecommerce.Utils.HandleCategory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private HandleCategory handleCategory;



    public List<ProductResponseDto> getAllProduct(){

        //fetching all products in db
        List<Product> fetchedProduct = productRepository.findAll();

        //declaring list for storing all response
        List<ProductResponseDto> productResponse = new ArrayList<>();

        //loop for mapping productEntity into productResponse and setting category name
        for(Product product : fetchedProduct){
            ProductResponseDto response = modelMapper.map(product, ProductResponseDto.class);

            if(response.getCategory() != null){
                response.setCategory(product.getCategory().getName());
            }

            productResponse.add(response);
        }


        return productResponse;
    }

    public ProductResponseDto createNewProduct(CreateProductDto product){

        //checking whether this category already exist or not
        Category category = handleCategory.checkExistingCategory(product.getCategory());

        //calculate the net price from provided prica and discount percentage
        Double discountPrice = (product.getDiscount_percentage()/100)* product.getPrice();
        Double discountedPrice = product.getPrice() - discountPrice;

        //creating product Entity object
        Product newProduct = new Product(
                product.getPname(),
                product.getProduct_description(),
                product.getPrice(),
                product.getDiscount_percentage(),
                discountedPrice,
                category);

        //fetching user details from the db to add the product
        User userDetail = userService.getUserFromUsername("sujal").orElseThrow(()->new RuntimeException("User credintial in invalid"));

        //setting user for new products
        newProduct.setUser(userDetail);

        //adding products in users product list
        userDetail.getProducts().add(newProduct);

        //save new product and catch the response
        Product savedProduct = productRepository.save(newProduct);

        //save the user with new product to the db
         userService.saveExistingUser(userDetail);

         //mapping productEntity into productResponseDto
         ProductResponseDto mappedResponse = modelMapper.map(savedProduct,ProductResponseDto.class);
          mappedResponse.setCategory(savedProduct.getCategory().getName());
          return mappedResponse;
    }

    public Page<Product> findFilteredProduct(String category, Pageable paging){

        return productRepository.findByCategoryContaining(category, paging);
    }

    public ProductResponseDto findById(Long id){

        Optional<Product> existingEntry =  productRepository.findById(id);
        if(existingEntry.isEmpty()){
            throw new ProductNotFoundException(id);
        }
        Product existingProduct = existingEntry.get();

        return modelMapper.map(existingProduct, ProductResponseDto.class);

    }

    public Product findProductById(Long id){

        Optional<Product> existingEntry =  productRepository.findById(id);
        if(existingEntry.isEmpty()){
            throw new ProductNotFoundException(id);
        }
        return existingEntry.get();
    }

    public boolean deleteProductByid(Long id){
        Optional<Product> existingProduct = productRepository.findById(id);
        if(existingProduct.isEmpty()){
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
        return true;
    }

    public Product updateProductById(UpdateProductDto product, Long id){
        Optional<Product> existingProduct = productRepository.findById(id);

        Double netPrice = getNetPrice(product, id, existingProduct);

        Product updatedProduct = new Product(
                product.getPname() != null ? product.getPname() : existingProduct.get().getPname(),
                product.getProduct_description() != null ? product.getProduct_description() : existingProduct.get().getProduct_description(),
                product.getPrice() != null ? product.getPrice() : existingProduct.get().getPrice(),
                product.getDiscount_percentage() != null ? product.getDiscount_percentage() : existingProduct.get().getDiscount_percentage(),
                 netPrice,
                product.getCategory() != null ? product.getCategory() : existingProduct.get().getCategory()


        );
         updatedProduct.setPid(id);
         return productRepository.save(updatedProduct);

    }

    private static Double getNetPrice(UpdateProductDto product, Long id, Optional<Product> existingProduct) {
        if(existingProduct.isEmpty()){
            throw new ProductNotFoundException(id);
        }

        // Get the existing product entity
        Product productEntity = existingProduct.get();

        Float updatedPercentage = product.getDiscount_percentage() != null ? product.getDiscount_percentage() : productEntity.getDiscount_percentage();
        Double updatedPrice = product.getPrice() != null ? product.getPrice() : productEntity.getPrice();

        return updatedPrice -  (updatedPercentage / 100) * updatedPrice;
    }
}
