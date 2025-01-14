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
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;



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

    @Transactional
    public ProductResponseDto createNewProduct(CreateProductDto product) throws IOException {

        //fetching user details from the db to add the product
        User userDetail = userService.getUserFromUsername("sujal").orElseThrow(()->new RuntimeException("User credintial in invalid"));

        //checking whether this category already exist or not
        Category category = handleCategory.checkExistingCategory(product.getCategory());

        //calculate the net price from provided prica and discount percentage
        Double discountPrice = (product.getDiscount()/100)* product.getPrice();
        Double discountedPrice = product.getPrice() - discountPrice;

        //saving image of product
        String imageUrl = fileService.uploadImage(path, product.getImage());

        //creating product Entity object
        Product newProduct = new Product(
                product.getPname(),
                product.getDescription(),
                product.getPrice(),
                product.getDiscount(),
                discountedPrice,
                category,
                imageUrl);

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

    @Transactional
    public boolean deleteProductByid(Long id) throws InterruptedException {
        Product existingProduct = productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException(id));

        //deleting the data in db
        productRepository.deleteById(id);

        //delete image before deleting the product
        fileService.deleteImage(path, existingProduct.getImage());
        return true;
    }

    @Transactional
    public Product updateProductById(UpdateProductDto product, Long id) throws IOException {
        Product existingProduct = productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException(id));

        //calculating net price after applying discount on price
        Double netPrice = getNetPrice(product, id, existingProduct);

        //if category is updated
        Category category = null;
        if(product.getCategory() != null){
            //checking whether this category already exist or not
             category = handleCategory.checkExistingCategory(product.getCategory());
        }else{
            //if category is not changed assign the previous category
            category = existingProduct.getCategory();
        }


        //checking whether the image is changed or not while updating
        //if changed then delete the existing one and upload new
        String updatedImageName = null;
        if(product.getImage() != null){
            fileService.deleteImage(path, existingProduct.getImage());
            updatedImageName = fileService.uploadImage(path, product.getImage());

        }


        Product updatedProduct = new Product(
                product.getPname() != null ? product.getPname() : existingProduct.getPname(),
                product.getDescription() != null ? product.getDescription() : existingProduct.getDescription(),
                product.getPrice() != null ? product.getPrice() : existingProduct.getPrice(),
                product.getDiscount() != null ? product.getDiscount() : existingProduct.getDiscount(),
                 netPrice,
                category,
                product.getImage() != null ? updatedImageName : existingProduct.getImage()

        );
         updatedProduct.setPid(id);
         return productRepository.save(updatedProduct);

    }

    private static Double getNetPrice(UpdateProductDto product, Long id, Product existingProduct) {

        Float updatedPercentage = product.getDiscount() != null ? product.getDiscount() : existingProduct.getDiscount();
        Double updatedPrice = product.getPrice() != null ? product.getPrice() : existingProduct.getPrice();

        return updatedPrice -  (updatedPercentage / 100) * updatedPrice;
    }
}

