package com.sujal.Ecommerce.Controller;

import com.sujal.Ecommerce.DTO.Response.ProductResponseDto;
import com.sujal.Ecommerce.DTO.Response.UserResponse;
import com.sujal.Ecommerce.Entity.Product;
import com.sujal.Ecommerce.Service.ProductService;
import com.sujal.Ecommerce.Service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin API")
@Slf4j
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUser(){
        try{
            List<UserResponse> user = userService.getAllUser();
            log.info("All users fetched successfully");
            return ResponseEntity.ok(user);
        }catch(Exception e){
            log.error("Error while fetching all users");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/users/role")
    public ResponseEntity<?> getUsersByRole(@RequestParam String role){
        try{
            List<UserResponse> user = userService.getUserByRole(role);
            log.info("All user having role: {} fecthed successfully.", role);
            return ResponseEntity.ok(user);
        }catch(Exception e){
            log.error("Error while fetching all user by admin", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts(){
        try{
            List<ProductResponseDto> product= productService.getAllProduct();
            log.info("Product fetched succesfully by Admin");
            return new ResponseEntity<>(product, HttpStatus.OK);
        }catch (Exception e){
            log.error("Failed to fetch product", e);
            return new ResponseEntity<>("Error while Fetching Products.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
