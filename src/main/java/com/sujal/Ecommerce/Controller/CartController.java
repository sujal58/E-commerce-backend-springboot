package com.sujal.Ecommerce.Controller;


import com.sujal.Ecommerce.Entity.Cart;
import com.sujal.Ecommerce.Entity.CartItem;
import com.sujal.Ecommerce.Entity.User;
import com.sujal.Ecommerce.Exceptions.InvalidArgumentException;
import com.sujal.Ecommerce.Exceptions.UserNotFoundException;
import com.sujal.Ecommerce.Service.CartService;
import com.sujal.Ecommerce.Service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@Tag(name = "Cart API")
@Slf4j
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCart(
            @PathVariable Long id
    ){
        Cart cart = cartService.getCartOfUser(id);
        return ResponseEntity.ok().body(cart);
    }

    @PostMapping("add/{productId}")
    public ResponseEntity<Cart> addToCart(
            @PathVariable Long productId,
            @RequestParam int quantity
    ){
        Long userId;

        if(quantity <= 0){
            log.warn("Quantity cannot be zero.");
            throw new InvalidArgumentException("Quantity must be greater than zero");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserFromUsername(auth.getName()).orElse(null);
        if(user == null){
            log.error("User not found while adding product to cart.");
             throw new UserNotFoundException();
        }
        userId = user.getId();
        Cart cart = cartService.addProductToCart(userId, productId, quantity);
        log.info("Product added to cart successfully");
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }


    @DeleteMapping("/dlt/{productId}")
    public ResponseEntity<?> deleteProduct(
            @PathVariable Long productId
    ){
        Long userId;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserFromUsername(auth.getName()).orElse(null);
        if(user == null){
            log.error("User not found while deleting product from cart");
            throw new UserNotFoundException();
        }
        userId = user.getId();

        List<CartItem> cartItem = cartService.deleteProductFromCart(userId, productId);
        log.info("Product having id: {} is deleted from cart.", productId);
        return ResponseEntity.ok(cartItem);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<?> updateQuantity( @PathVariable Long productId, @RequestParam int quantity){
        Long userId;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserFromUsername(auth.getName()).orElse(null);
        if(user == null){
            log.error("User not found while updating cart");
            throw new UserNotFoundException();
        }
        userId = user.getId();

        CartItem item = cartService.updateQuantity(userId, productId, quantity);
        log.info("Cart updated successfully.");
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

}
