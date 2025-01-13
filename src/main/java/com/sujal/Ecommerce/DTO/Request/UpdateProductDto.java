package com.sujal.Ecommerce.DTO.Request;

import com.sujal.Ecommerce.Entity.Category;

public class UpdateProductDto {

    private String pname;


    private String product_description;


    private Double price;


    private Float discount_percentage;


    private Category category;


    public UpdateProductDto(String pname, String product_description, Double price, Float discount_percentage, Category category) {
        this.pname = pname;
        this.product_description = product_description;
        this.price = price;
        this.discount_percentage = discount_percentage;
        this.category = category;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Float getDiscount_percentage() {
        return discount_percentage;
    }

    public void setDiscount_percentage(Float discount_percentage) {
        this.discount_percentage = discount_percentage;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
