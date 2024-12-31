package com.sujal.Ecommerce.Controller;

import com.sujal.Ecommerce.DTO.Request.CreateProductDto;
import com.sujal.Ecommerce.DTO.Request.UpdateProductDto;
import com.sujal.Ecommerce.DTO.Response.ProductResponse;
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

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAllProduct(){
        try{
            List<ProductResponse> products = productService.getAllProduct();
            return ResponseEntity.ok().body(products);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @PostMapping("/create")
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

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteProduct(@RequestParam Long pid){
        try{
            productService.deleteProductByid(pid);
        }catch (Exception e){
            if(e.getMessage().equals("Product doesnot Exist")){
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return ResponseEntity.ok("Product deleted Successfully");

    }

    @PutMapping("/update/{pid}")
    public ResponseEntity<?> updateProductById(@Valid @RequestBody UpdateProductDto product, @PathVariable Long pid){

        try{
            ProductEntity updatedProduct = productService.updateProductById(product, pid);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        }catch (Exception e){
            if(e.getMessage().equals("Product doesnot Exist")){
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
}
