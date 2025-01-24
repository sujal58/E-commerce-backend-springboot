package com.sujal.Ecommerce.DTO.Request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    private String currency;

    private Double totalPrice;

    private int quantity;

    private String description;
}
