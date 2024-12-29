package com.sujal.Ecommerce.Service;


import com.sujal.Ecommerce.DTO.Request.CreateProductDto;
import com.sujal.Ecommerce.Entity.ProductEntity;
import com.sujal.Ecommerce.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;



import java.util.List;

@Component
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    public List<ProductEntity> getAllProduct(){
        return productRepository.findAll();
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
}
