package com.sujal.Ecommerce.Service;

import com.sujal.Ecommerce.DTO.Response.ProductResponseDto;
import com.sujal.Ecommerce.Entity.Cart;
import com.sujal.Ecommerce.Entity.CartItem;
import com.sujal.Ecommerce.Entity.Product;
import com.sujal.Ecommerce.Entity.User;
import com.sujal.Ecommerce.Exceptions.ResouceNotFound;
import com.sujal.Ecommerce.Repository.CartItemRepository;
import com.sujal.Ecommerce.Repository.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;


    public Cart getCartOfUser(Long id){
        User userExist = userService.getUserById(id);
        return cartRepository.findByUser_Id(id);
    }

    public Cart addProductToCart(Long userId,Long productId, int quantity){
        Product product = productService.findProductById(productId);
        Cart cart = cartRepository.findByUser_Id(userId);
        User user = userService.getUserById(userId);

        if(cart == null){
            cart = new Cart();
            cart.setUser(user);
            cart.setTotalPrice(0.0);
            cart.setQuantity(0);
        }

        CartItem item = cart.getCartItem().stream()
                .filter(items -> items.getProduct().getPid().equals(productId))
                .findFirst()
                .orElse(null);

        if (item == null) {
            //if product is not in the cart, create a new cartItem
             item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(quantity);
            item.setPrice(quantity * product.getNetPrice());

            //add new item to cart
            cart.getCartItem().add(item);

            //update cart's total quatity and total price
            cart.setQuantity(cart.getQuantity()+quantity);
            cart.setTotalPrice(item.getPrice() + cart.getTotalPrice());
        }else{
            //if product is already in the cart, just update quantity and price
            item.setQuantity(item.getQuantity()+quantity);
            item.setPrice(item.getQuantity() * product.getNetPrice());

            //update cart's total quantity and total price
            cart.setQuantity(cart.getQuantity() + quantity);
            cart.setTotalPrice(cart.getTotalPrice() + (product.getNetPrice() * quantity));
        }

        //save the updated cart
        return cartRepository.save(cart);
    }

    @Transactional
    public List<CartItem> deleteProductFromCart(Long userId, Long pid){
        Cart cart = cartRepository.findByUser_Id(userId);
        
        CartItem itemsToRemove = cart.getCartItem().stream()
                .filter(items -> items.getProduct().getPid().equals(pid)).findFirst().orElse(null);

        if(itemsToRemove != null){
            cart.setQuantity(cart.getQuantity() - itemsToRemove.getQuantity());
            cart.setTotalPrice(cart.getTotalPrice() - itemsToRemove.getPrice());
            cart.getCartItem().remove(itemsToRemove);
            cartItemRepository.delete(itemsToRemove);
            cartRepository.save(cart);
        }

        return cart.getCartItem();
    }

    public CartItem updateQuantity(Long userId, Long productId, int quantity){
        userService.getUserById(userId);
        Cart cart = cartRepository.findByUser_Id(userId);

        if(cart == null){
            throw new ResouceNotFound("Cart");
        }

        Optional<CartItem> existingItem = cart.getCartItem()
                .stream().filter(items-> items.getProduct().getPid().equals(productId)).findFirst();

        if(existingItem.isPresent()){
            if(existingItem.get().getQuantity() != quantity){
                Double updatedPrice = existingItem.get().getProduct().getNetPrice() * quantity;
                cart.setTotalPrice(cart.getTotalPrice() - existingItem.get().getPrice() + updatedPrice);
                cart.setQuantity(cart.getQuantity() - existingItem.get().getQuantity() + quantity);
                existingItem.get().setQuantity(quantity);
                existingItem.get().setPrice(updatedPrice);
//                cartItemRepository.save(existingItem.get());
                cartRepository.save(cart);
            }
            return existingItem.get();
        }

        return null;
    }
}
