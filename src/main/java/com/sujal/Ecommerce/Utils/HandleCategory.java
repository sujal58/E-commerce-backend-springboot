package com.sujal.Ecommerce.Utils;

import com.sujal.Ecommerce.Entity.CategoryEntity;
import com.sujal.Ecommerce.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HandleCategory {

    @Autowired
    private CategoryService categoryService;

    public CategoryEntity checkExistingCategory(String categoryName){
        //check if the category of this product is already exist or not
        CategoryEntity category = categoryService.findByCategoryName(categoryName);
        CategoryEntity savedCategory = new CategoryEntity();

        //if category is not available then create new category and save into db
        if(category == null){
            CategoryEntity newCategory = new CategoryEntity();
            newCategory.setName(categoryName);
            savedCategory = categoryService.saveCategory(newCategory);
        }else{
            savedCategory = category;
        }

        return savedCategory;
    }
}
