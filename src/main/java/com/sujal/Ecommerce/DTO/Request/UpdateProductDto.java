package com.sujal.Ecommerce.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductDto {

    private String pname;


    private String description;


    private Double price;


    private Float discount;


    private String category;


    private MultipartFile image;


}
