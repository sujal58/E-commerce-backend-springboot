package com.sujal.Ecommerce.DTO;

import java.util.List;

public class LoginResponseDto {
    private String token;
    private String username;
    private List<String> role;

    public LoginResponseDto(String token, String username, List<String> role) {
        this.token = token;
        this.username = username;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }
}
