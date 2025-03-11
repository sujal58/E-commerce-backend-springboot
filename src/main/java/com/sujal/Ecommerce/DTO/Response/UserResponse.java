package com.sujal.Ecommerce.DTO.Response;

import com.sujal.Ecommerce.Entity.Product;
import com.sujal.Ecommerce.Enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private List<Role> role;
}
