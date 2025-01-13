package com.sujal.Ecommerce.Service;

import com.sujal.Ecommerce.Entity.Category;
import com.sujal.Ecommerce.Exceptions.ResouceNotFound;
import com.sujal.Ecommerce.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Component
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category saveCategory(Category category){
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategory(){
        return categoryRepository.findAll();

    }

    public Category findByCategoryName(String categoryName){
        Category category = categoryRepository.findByname(categoryName).orElse(null);
         if(category == null){
             throw new ResouceNotFound("Category");
         }
         return category;
    }
}
