package com.sujal.Ecommerce.Repository;

import com.sujal.Ecommerce.Entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


import java.util.Optional;

@Component
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findBypname(String name);

    Page<ProductEntity> findByCategoryContaining(String category, Pageable pageable);
}
