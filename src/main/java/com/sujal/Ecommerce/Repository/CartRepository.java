package com.sujal.Ecommerce.Repository;

import com.sujal.Ecommerce.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByUser_Id(Long id);

//    Cart findByUsername(String username);


}
