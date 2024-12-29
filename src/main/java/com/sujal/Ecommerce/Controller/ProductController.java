package com.sujal.Ecommerce.Controller;

import com.sujal.Ecommerce.DTO.Request.CreateProductDto;
import com.sujal.Ecommerce.Entity.ProductEntity;
import com.sujal.Ecommerce.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAllProduct(){
        try{
            return ResponseEntity.ok().body(productService.getAllProduct());
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @PostMapping("/create-product")
    public ResponseEntity<?> createNewProduct(@Valid @RequestBody CreateProductDto product){
        try{
              ProductEntity createdProduct = productService.createNewProduct(product);
               return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
//
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<?> getFilteredProduct(
            @RequestParam String category
    ){
        try{
            Pageable paging = PageRequest.of(0, 2);
            Page<ProductEntity> productPage;

            if(category.isEmpty()){
                return ResponseEntity.ok(productService.getAllProduct());
            }else{
                productPage = productService.findFilteredProduct(category, paging);
                System.out.println(productPage.getTotalPages());
                return ResponseEntity.ok(productPage);
            }
        }catch (Exception e){
           return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
