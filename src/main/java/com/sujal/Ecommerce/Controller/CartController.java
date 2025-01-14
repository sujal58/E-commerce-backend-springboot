package com.sujal.Ecommerce.Controller;


import com.sujal.Ecommerce.Entity.Cart;
import com.sujal.Ecommerce.Entity.CartItem;
import com.sujal.Ecommerce.Entity.Product;
import com.sujal.Ecommerce.Entity.User;
import com.sujal.Ecommerce.Repository.CartRepository;
import com.sujal.Ecommerce.Service.CartService;
import com.sujal.Ecommerce.Service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@Tag(name = "Cart API")
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

    @PostMapping("add/{userId}/{productId}")
    public ResponseEntity<Cart> addToCart(
            @PathVariable Long userId,
            @PathVariable Long productId,
            @RequestParam int quantity
    ){
        Cart cart = cartService.addProductToCart(userId, productId, quantity);
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }

    @DeleteMapping("/dlt/{userId}/{productId}")
    public ResponseEntity<?> deleteProduct(
            @PathVariable Long userId,
            @PathVariable Long productId
    ){
        List<CartItem> cartItem = cartService.deleteProductFromCart(userId, productId);
        return ResponseEntity.ok(cartItem);
    }

    @PutMapping("/update/{userId}/{productId}")
    public ResponseEntity<?> updateQuantity(@PathVariable Long userId, @PathVariable Long productId, @RequestParam int quantity){
        CartItem item = cartService.updateQuantity(userId, productId, quantity);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

}
