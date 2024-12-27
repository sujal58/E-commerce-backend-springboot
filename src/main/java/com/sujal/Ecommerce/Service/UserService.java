package com.sujal.Ecommerce.Service;

import com.sujal.Ecommerce.Entity.UserEntity;
import com.sujal.Ecommerce.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder(10);

    public UserEntity createNewUser(UserEntity user){

            user.setRole(List.of("USER"));
            user.setPassword(bcrypt.encode(user.getPassword()));
            return userRepository.save(user);

    }

    public List<UserEntity> getAllUser(){
        return userRepository.findAll();
    }
}
