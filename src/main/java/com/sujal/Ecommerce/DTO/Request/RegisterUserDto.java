package com.sujal.Ecommerce.DTO.Request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

public class RegisterUserDto {

    @NotNull(message = "Username cannot be null")
    private String username;

    @NotNull(message = "Password cannot be null")
    private String password;

    @NotNull(message = "Email cannot be null")
    private String email;


    public @NotNull(message = "Username cannot be null") String getUsername() {
        return username;
    }

    public void setUsername(@NotNull(message = "Username cannot be null") String username) {
        this.username = username;
    }

    public @NotNull(message = "Password cannot be null") String getPassword() {
        return password;
    }

    public void setPassword(@NotNull(message = "Password cannot be null") String password) {
        this.password = password;
    }

    public @NotNull(message = "Email cannot be null") String getEmail() {
        return email;
    }

    public void setEmail(@NotNull(message = "Email cannot be null") String email) {
        this.email = email;
    }
}
