package com.sujal.Ecommerce.Service;

import com.sujal.Ecommerce.Entity.UserEntity;
import com.sujal.Ecommerce.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder(10);

    public UserEntity createNewUser(UserEntity user){

            Optional<UserEntity> existingUser = userRepository.findByUsername(user.getUsername());
            if(existingUser.isPresent()){
                throw new RuntimeException("User already Exist");
            }

            user.setRole(List.of("USER"));
            user.setPassword(bcrypt.encode(user.getPassword()));
            return userRepository.save(user);

    }

    public List<UserEntity> getAllUser(){
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUserFromUsername(String username){
        return userRepository.findByUsername(username);
    }
}
