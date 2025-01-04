package com.sujal.Ecommerce.Service;

import com.sujal.Ecommerce.Entity.CategoryEntity;
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

    public CategoryEntity saveCategory(CategoryEntity category){
        return categoryRepository.save(category);
    }

    public List<CategoryEntity> getAllCategory(){
        return categoryRepository.findAll();

    }

    public CategoryEntity findByCategoryName(String categoryName){
        Optional<CategoryEntity> category = categoryRepository.findByname(categoryName);
        return category.orElse(null);
    }
}
