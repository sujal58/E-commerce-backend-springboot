package com.sujal.Ecommerce.DTO.Response;

import com.sujal.Ecommerce.Entity.ProductEntity;

import java.util.List;

public class UserResponse {
    private String username;
    private String email;
    private List<String> role;
    private List<ProductEntity> products;

    public UserResponse() {
    }

    public UserResponse(String username, String email, List<String> role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }
}
