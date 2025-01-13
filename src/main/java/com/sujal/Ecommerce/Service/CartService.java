package com.sujal.Ecommerce.Service;

import com.sujal.Ecommerce.DTO.Response.ProductResponseDto;
import com.sujal.Ecommerce.Entity.Cart;
import com.sujal.Ecommerce.Entity.CartItem;
import com.sujal.Ecommerce.Entity.Product;
import com.sujal.Ecommerce.Entity.User;
import com.sujal.Ecommerce.Repository.CartItemRepository;
import com.sujal.Ecommerce.Repository.CartRepository;
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
            item.setPrice(quantity * product.getNet_price());

            //add new item to cart
            cart.getCartItem().add(item);

            //update cart's total quatity and total price
            cart.setQuantity(cart.getQuantity()+quantity);
            cart.setTotalPrice(item.getPrice() + cart.getTotalPrice());
        }else{
            //if product is already in the cart, just update quantity and price
            item.setQuantity(item.getQuantity()+quantity);
            item.setPrice(item.getQuantity() * product.getNet_price());

            //update cart's total quantity and total price
            cart.setQuantity(cart.getQuantity() + quantity);
            cart.setTotalPrice(cart.getTotalPrice() + (product.getNet_price() * quantity));
        }

        //save the updated cart
        return cartRepository.save(cart);
    }

    public List<CartItem> deleteProductFromCart(Long userId, Long pid){
        Cart cart = cartRepository.findByUser_Id(userId);
        
        CartItem itemsToRemove = cart.getCartItem().stream()
                .filter(product -> product.getId() == pid).findFirst().orElse(null);

        if(itemsToRemove != null){
            cart.setQuantity(cart.getQuantity() - itemsToRemove.getQuantity());
            cart.setTotalPrice(cart.getTotalPrice() - itemsToRemove.getPrice());

            cart.getCartItem().remove(itemsToRemove);

            cartRepository.save(cart);
        }

        return cart.getCartItem();
    }
}
