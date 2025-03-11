package com.sujal.Ecommerce.Controller;

import com.stripe.Stripe;
import com.stripe.model.Price;
import com.sujal.Ecommerce.DTO.Request.PaymentRequest;
import com.sujal.Ecommerce.Entity.Cart;
import com.sujal.Ecommerce.Entity.User;
import com.sujal.Ecommerce.Exceptions.UserNotFoundException;
import com.sujal.Ecommerce.Service.CartService;
import com.sujal.Ecommerce.Service.PaymentService;
import com.sujal.Ecommerce.Service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payment/checkout")
@Slf4j
public class PaymentController {

    @Value("${Stripe.publishable.Key}")
    private String publishable_key;

    @Value("${Stripe.secret.Key}")
    private String private_key;

    @PostConstruct
    public void init(){
        Stripe.apiKey = private_key;
    }

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserService userService;



    @PostMapping()
    public ResponseEntity<?> checkout( @RequestBody PaymentRequest payment, @RequestParam String currency){

        Long userId;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserFromUsername(auth.getName()).orElse(null);
        if(user == null){
            log.error("user not found while processing payment");
            throw new UserNotFoundException();
        }
        userId = user.getId();
        Price product = paymentService.createProduct(userId, payment.getDescription(), currency.toUpperCase());
        String paymentUrl = paymentService.checkOutProducts(userId, product.getId());
        log.info("Payment URl generated succesfully.");
        return ResponseEntity.ok(paymentUrl);
    }



}
