package com.sujal.Ecommerce.DTO.Request;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;


public class CreateProductDto {

    @NotNull(message = "Product name cannot be null")
    private String pname;

    @NotNull(message = "Product Description cannot be null")
    private String description;

    @NotNull(message = "Product price cannot be null")
    private Double price;

    @NotNull(message = "Product discount percentage cannot be null")
    private Float discount;

    @NotNull(message = "Product category cannot be null")
    private String category;

    @NotNull(message = "Image cannot be null")
    private MultipartFile image;

    public CreateProductDto(String pname, String description, Double price, Float discount, String category, MultipartFile image) {
        this.pname = pname;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.category = category;
        this.image = image;
    }

    public @NotNull(message = "Product name cannot be null") String getPname() {
        return pname;
    }

    public void setPname(@NotNull(message = "Product name cannot be null") String pname) {
        this.pname = pname;
    }

    public @NotNull(message = "Product Description cannot be null") String getDescription() {
        return description;
    }

    public void setDescription(@NotNull(message = "Product Description cannot be null") String description) {
        this.description = description;
    }

    public @NotNull(message = "Product price cannot be null") Double getPrice() {
        return price;
    }

    public void setPrice(@NotNull(message = "Product price cannot be null") Double price) {
        this.price = price;
    }

    @NotNull(message = "Product discount percentage cannot be null")
    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(@NotNull(message = "Product discount percentage cannot be null") Float discount) {
        this.discount = discount;
    }

    public @NotNull(message = "Product category cannot be null") String getCategory() {
        return category;
    }

    public void setCategory(@NotNull(message = "Product category cannot be null") String category) {
        this.category = category;
    }

    public @NotNull(message = "Image cannot be null") MultipartFile getImage() {
        return image;
    }

    public void setImage(@NotNull(message = "Image cannot be null") MultipartFile image) {
        this.image = image;
    }
}


