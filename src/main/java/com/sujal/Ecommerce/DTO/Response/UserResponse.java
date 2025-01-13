package com.sujal.Ecommerce.DTO.Response;

import com.sujal.Ecommerce.Entity.Product;
import com.sujal.Ecommerce.Enums.Role;

import java.util.List;

public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private List<Role> role;
//    private List<Product> products;

    public UserResponse() {
    }

    public UserResponse(Long id, String username, String email, List<Role> role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    //    public List<Product> getProducts() {
//        return products;
//    }
//
//    public void setProducts(List<Product> products) {
//        this.products = products;
//    }

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

    public List<Role> getRole() {
        return role;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }
}
