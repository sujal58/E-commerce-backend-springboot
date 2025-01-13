package com.sujal.Ecommerce.Repository;

import com.sujal.Ecommerce.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


import java.util.Optional;

@Component
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findBypname(String name);

    Page<Product> findByCategoryContaining(String category, Pageable pageable);
}
