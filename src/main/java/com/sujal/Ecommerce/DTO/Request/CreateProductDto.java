package com.sujal.Ecommerce.DTO.Request;
import jakarta.validation.constraints.NotNull;


public class CreateProductDto {

    @NotNull(message = "Product name cannot be null")
    private String pname;

    @NotNull(message = "Product Description cannot be null")
    private String product_description;

    @NotNull(message = "Product price cannot be null")
    private Double price;

    @NotNull(message = "Product discount percentage cannot be null")
    private Float discount_percentage;

    @NotNull(message = "Product category cannot be null")
    private String category;


    public @NotNull(message = "Product name cannot be null") String getPname() {
        return pname;
    }

    public void setPname(@NotNull(message = "Product name cannot be null") String pname) {
        this.pname = pname;
    }

    public @NotNull(message = "Product Description cannot be null") String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(@NotNull(message = "Product Description cannot be null") String product_description) {
        this.product_description = product_description;
    }

    public @NotNull(message = "Product price cannot be null") Double getPrice() {
        return price;
    }

    public void setPrice(@NotNull(message = "Product price cannot be null") Double price) {
        this.price = price;
    }

    @NotNull(message = "Product discount percentage cannot be null")
    public Float getDiscount_percentage() {
        return discount_percentage;
    }

    public void setDiscount_percentage(@NotNull(message = "Product discount percentage cannot be null") Float discount_percentage) {
        this.discount_percentage = discount_percentage;
    }

    public @NotNull(message = "Product category cannot be null") String getCategory() {
        return category;
    }

    public void setCategory(@NotNull(message = "Product category cannot be null") String category) {
        this.category = category;
    }
}


