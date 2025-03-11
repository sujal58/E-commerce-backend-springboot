package com.sujal.Ecommerce.Controller;

import com.sujal.Ecommerce.DTO.Request.ProductDto;
import com.sujal.Ecommerce.DTO.Request.UpdateProductDto;
import com.sujal.Ecommerce.DTO.Response.ApiResponse;
import com.sujal.Ecommerce.DTO.Response.ProductResponseDto;
import com.sujal.Ecommerce.Entity.Product;
import com.sujal.Ecommerce.Exceptions.InvalidArgumentException;
import com.sujal.Ecommerce.Exceptions.ProductNotFoundException;
import com.sujal.Ecommerce.Service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/products")
@Tag(name = "Product API", description = "Product related endpoints.")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAllProduct(){
        log.info("Fetching all Products.");
        try{
            List<ProductResponseDto> products = productService.getAllProduct();
            return ResponseEntity.ok().body(products);
        }catch (Exception e){
            log.error("Error while fetching a products", e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }



    @GetMapping("/{pid}")
    public ResponseEntity<?> getProductById(@PathVariable Long pid){
        try{
            ProductResponseDto product = productService.findById(pid);
            if(product == null){
                log.warn("Product having id:{} doesn't exist", pid);
                throw new ProductNotFoundException(pid);
            }
            return new ResponseEntity<>(ApiResponse.success(HttpStatus.OK, "Product fetched successfully", product), HttpStatus.OK);
        }catch (Exception e){
            log.error("Error while searching product", e);
            throw new ProductNotFoundException(pid);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNewProduct(
            @Valid @ModelAttribute ProductDto product,
            @RequestParam("image") MultipartFile file
            )
    {
        log.info("Creating new products.");
        if(file.isEmpty()){
            log.error("file cannot be empty.");
            throw new InvalidArgumentException("Image field cannot be Empty");
        }

        try{
            ProductResponseDto createdProduct = productService.createNewProduct(product, file);
            log.info("Product created successfully");
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
//
        }catch (Exception e){
            log.error("Error while creating product", e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<?> getFilteredProduct(
            @RequestParam String category
    ){
        log.info("Filtering products by category.");
        try{
            Pageable paging = PageRequest.of(0, 2);
            Page<Product> productPage;

            if(category.isEmpty()){
                log.warn("Category param cannot be empty");
                return ResponseEntity.ok(productService.getAllProduct());
            }else{
                productPage = productService.findFilteredProduct(category, paging);
                System.out.println(productPage.getTotalPages());
                log.info("Product of category: {} is fetched successfully", category);
                return ResponseEntity.ok(productPage);
            }
        }catch (Exception e){
            log.error("Error while filtering products.");
           return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteProduct(@RequestParam Long pid) throws InterruptedException {
        try{
            productService.deleteProductByid(pid);

        }catch(ProductNotFoundException e){
            log.error("Product having id: {} is not found.", pid);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error("Error while deleting product having id: {}", pid);
        }
        log.info("Product deleted successfully");
        return ResponseEntity.ok("Product deleted Successfully");
    }

    @PutMapping("/update/{pid}")
    public ResponseEntity<?> updateProductById(
            @Valid @ModelAttribute UpdateProductDto product,
            @RequestParam("image") MultipartFile file,
             @PathVariable Long pid){

        try{
            Product updatedProduct = productService.updateProductById(product, pid);
            log.info("Product updated successfully");
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        }catch (ProductNotFoundException e){
            log.error("Product not found");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
        }catch(Exception e){
            log.error("Error while updating product", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
