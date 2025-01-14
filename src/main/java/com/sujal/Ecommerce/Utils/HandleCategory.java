package com.sujal.Ecommerce.Utils;

import com.sujal.Ecommerce.Entity.Category;
import com.sujal.Ecommerce.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HandleCategory {

    @Autowired
    private CategoryService categoryService;

    public Category checkExistingCategory(String categoryName){
        //check if the category of this product is already exist or not
        Category category = categoryService.findByCategoryName(categoryName);

        Category savedCategory = new Category();

        //if category is not available then create new category and save into db
        if(category == null){
            Category newCategory = new Category();
            newCategory.setName(categoryName);
            savedCategory = categoryService.saveCategory(newCategory);
        }else{
            savedCategory = category;
        }

        return savedCategory;
    }
}
