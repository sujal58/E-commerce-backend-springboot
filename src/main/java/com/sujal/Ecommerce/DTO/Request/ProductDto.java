package com.sujal.Ecommerce.DTO.Request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    @NotBlank(message = "Product name cannot be null")
    private String name;

    @NotBlank(message = "Product Description cannot be null")
    private String description;

    @NotNull(message = "Product price cannot be null")
    private Double price;

    @NotNull(message = "Product discount percentage cannot be null")
    private Float discount;

    @NotBlank(message = "Product category cannot be null")
    private String category;

}


