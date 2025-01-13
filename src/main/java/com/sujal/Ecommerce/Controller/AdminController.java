package com.sujal.Ecommerce.Controller;

import com.sujal.Ecommerce.Service.ProductService;
import com.sujal.Ecommerce.Service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin API")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUser(){
        try{
            return ResponseEntity.ok(userService.getAllUser());
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/users/role")
    public ResponseEntity<?> getUsersByRole(@RequestParam String role){
        try{
            return ResponseEntity.ok(userService.getUserByRole(role));
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts(){
        try{
            return new ResponseEntity<>(productService.getAllProduct(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Error while Fetching Products.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
