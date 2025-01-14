package com.sujal.Ecommerce.DTO.Request;

import com.sujal.Ecommerce.Entity.Category;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public class UpdateProductDto {

    private String pname;


    private String description;


    private Double price;


    private Float discount;


    private String category;


    private MultipartFile image;

    public UpdateProductDto(String pname, String description, Double price, Float discount, String category, MultipartFile image) {
        this.pname = pname;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.category = category;
        this.image = image;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
