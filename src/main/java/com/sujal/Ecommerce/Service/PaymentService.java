package com.sujal.Ecommerce.Service;

import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import com.sujal.Ecommerce.DTO.Request.PaymentRequest;
import com.sujal.Ecommerce.Entity.Cart;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Value("${Stripe.base.url}")
    private String url;

    @Autowired
    private CartService cartService;

    @Autowired
    private ModelMapper modelMapper;

    public Price createProduct(Long userId,String description, String currency){

        Cart cartToCheckout = cartService.getCartOfUser(userId);

        PaymentRequest mappedRequest = modelMapper.map(cartToCheckout, PaymentRequest.class);
        mappedRequest.setDescription(description);

        try{
            ProductCreateParams product = ProductCreateParams.builder()
                    .setName("Payment checkout")
                    .setDescription(description)
                    .setType(ProductCreateParams.Type.GOOD)
                    .build();

            Product createdProduct = Product.create(product);


            //creating price for the product created above
            PriceCreateParams price = PriceCreateParams.builder()
                    .setCurrency(currency == null ? "USD" : currency)
                    .setUnitAmount((long)(double)mappedRequest.getTotalPrice() * 100)
                    .setProduct(createdProduct.getId())
                    .build();

            return Price.create(price);

        }catch (StripeException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }


    public String checkOutProducts(Long userId, String priceId){
        Cart cartToCheckout = cartService.getCartOfUser(userId);

        try{
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(url+"?success=true")
                .setCancelUrl(url+"?success=false")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity((long)cartToCheckout.getQuantity())
                                .setPrice(priceId)
                                .build()
                )
                .build();

            Session session = Session.create(params);
            return session.getUrl();
        }catch (StripeException ex){
            return ex.getMessage();
        }
    }
}
