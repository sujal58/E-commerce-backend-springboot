package com.sujal.Ecommerce.Controller;


import com.sujal.Ecommerce.Entity.Category;
import com.sujal.Ecommerce.Entity.Product;

import com.sujal.Ecommerce.Exceptions.ResouceNotFound;
import com.sujal.Ecommerce.Service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Category API")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category")
    public ResponseEntity<List<Category>> getAllCategory(){
         return ResponseEntity.ok(categoryService.getAllCategory());
    }

    @GetMapping
    public ResponseEntity<?> getProductsByCategory(
            @RequestParam String category
    ){
        Category existingCategory = categoryService.findByCategoryName(category);
        if(existingCategory == null){
            throw new ResouceNotFound("Category of name:"+ category);
        }
        return ResponseEntity.ok(existingCategory.getProduct());
    }
}
