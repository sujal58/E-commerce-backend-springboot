package com.sujal.Ecommerce.Controller;

import com.stripe.Stripe;
import com.stripe.model.Price;
import com.sujal.Ecommerce.DTO.Request.PaymentRequest;
import com.sujal.Ecommerce.Entity.Cart;
import com.sujal.Ecommerce.Service.CartService;
import com.sujal.Ecommerce.Service.PaymentService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payment/checkout")
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



    @PostMapping("/{userId}")
    public ResponseEntity<?> checkout(@PathVariable Long userId, @RequestBody PaymentRequest payment, @RequestParam String currency){

        Price product = paymentService.createProduct(userId, payment.getDescription(), currency.toUpperCase());
        String paymentUrl = paymentService.checkOutProducts(userId, product.getId());
        return ResponseEntity.ok(paymentUrl);
    }


}
