package com.sujal.Ecommerce.Repository;

import com.sujal.Ecommerce.Entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
