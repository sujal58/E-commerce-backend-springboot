package com.sujal.Ecommerce.Controller;

import com.sujal.Ecommerce.DTO.Request.CreateProductDto;
import com.sujal.Ecommerce.DTO.Request.UpdateProductDto;
import com.sujal.Ecommerce.DTO.Response.ProductResponseDto;
import com.sujal.Ecommerce.Entity.Product;
import com.sujal.Ecommerce.Exceptions.InvalidArgumentException;
import com.sujal.Ecommerce.Exceptions.ProductNotFoundException;
import com.sujal.Ecommerce.Service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAllProduct(){
        try{
            List<ProductResponseDto> products = productService.getAllProduct();
            return ResponseEntity.ok().body(products);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }



    @GetMapping("/{pid}")
    public ResponseEntity<?> getProductById(@PathVariable Long pid){
        throw new ProductNotFoundException(pid);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNewProduct(
            @Valid @RequestParam String pName,
            @Valid @RequestParam String description,
            @Valid @RequestParam Double price,
            @Valid @RequestParam Float discount,
            @Valid @RequestParam String category,
            @RequestParam("image") MultipartFile file
            )
    {
        if(file.isEmpty()){
            throw new InvalidArgumentException("Image field cannot be Empty");
        }

        try{
            CreateProductDto product = new CreateProductDto(pName, description, price, discount, category, file);
            ProductResponseDto createdProduct = productService.createNewProduct(product);
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
            Page<Product> productPage;

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
    public ResponseEntity<?> deleteProduct(@RequestParam Long pid) throws InterruptedException {
            productService.deleteProductByid(pid);
            return ResponseEntity.ok("Product deleted Successfully");

    }

    @PutMapping("/update/{pid}")
    public ResponseEntity<?> updateProductById(
             @RequestParam String pname,
             @RequestParam String description,
             @RequestParam Double price,
             @RequestParam Float discount,
             @RequestParam String category,
             @RequestParam("image") MultipartFile file,
             @PathVariable Long pid){

        try{
            UpdateProductDto product = new UpdateProductDto(pname, description, price, discount, category, file);
            Product updatedProduct = productService.updateProductById(product, pid);
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
