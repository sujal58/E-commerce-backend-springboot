package com.sujal.Ecommerce.Service;


import com.sujal.Ecommerce.DTO.Request.CreateProductDto;
import com.sujal.Ecommerce.DTO.Request.UpdateProductDto;
import com.sujal.Ecommerce.DTO.Response.ProductResponse;
import com.sujal.Ecommerce.Entity.ProductEntity;
import com.sujal.Ecommerce.Exceptions.ResourceNotFoundExcption;
import com.sujal.Ecommerce.Repository.ProductRepository;
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


    public List<ProductResponse> getAllProduct(){
        List<ProductEntity> fetchedProduct = productRepository.findAll();
        List<ProductResponse> productResponse = new ArrayList<>(fetchedProduct.stream()
                .map(product -> modelMapper.map(product, ProductResponse.class)).toList());
        return productResponse;
    }

    public ProductEntity createNewProduct(CreateProductDto product){

        Double discountPrice = (product.getDiscount_percentage()/100)* product.getPrice();
        Double discountedPrice = product.getPrice() - discountPrice;
        ProductEntity newProduct = new ProductEntity(product.getPname(), product.getProduct_description(),product.getPrice(),product.getDiscount_percentage(), discountedPrice, product.getCategory());
        return productRepository.save(newProduct);

    }

    public Page<ProductEntity> findFilteredProduct(String category, Pageable paging){

        return productRepository.findByCategoryContaining(category, paging);
    }

    public ProductResponse findById(Long id){

        Optional<ProductEntity> existingEntry =  productRepository.findById(id);
        if(existingEntry.isEmpty()){
            throw new ResourceNotFoundExcption("Product", id);
        }
        ProductEntity existingProduct = existingEntry.get();

        return modelMapper.map(existingProduct, ProductResponse.class);

    }

    public boolean deleteProductByid(Long id){
        Optional<ProductEntity> existingProduct = productRepository.findById(id);
        if(existingProduct.isEmpty()){
            throw new ResourceNotFoundExcption("Product", id);
        }
        productRepository.deleteById(id);
        return true;
    }

    public ProductEntity updateProductById(UpdateProductDto product, Long id){
        Optional<ProductEntity> existingProduct = productRepository.findById(id);

        Double netPrice = getNetPrice(product, id, existingProduct);

        ProductEntity updatedProduct = new ProductEntity(
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

    private static Double getNetPrice(UpdateProductDto product, Long id, Optional<ProductEntity> existingProduct) {
        if(existingProduct.isEmpty()){
            throw new ResourceNotFoundExcption("Product", id);
        }

        // Get the existing product entity
        ProductEntity productEntity = existingProduct.get();

        Float updatedPercentage = product.getDiscount_percentage() != null ? product.getDiscount_percentage() : productEntity.getDiscount_percentage();
        Double updatedPrice = product.getPrice() != null ? product.getPrice() : productEntity.getPrice();

        return updatedPrice -  (updatedPercentage / 100) * updatedPrice;
    }
}
