package com.sujal.Ecommerce.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    @NotBlank(message = "Currency should be mentioned.")
    private String currency;

    @NotNull(message = "Total price is Empty.")
    private Double totalPrice;

    @NotNull(message = "Quantity is zero.")
    private int quantity;

    @NotBlank(message = "Must have a description.")
    private String description;
}
