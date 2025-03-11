package com.sujal.Ecommerce.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {

    private Long pid;

    private String pname;

    private String description;

    private Double price;

    private Float discount;

    private Double netPrice;

    private String category;

    private String image;

}
