package com.sujal.Ecommerce.Service;

import com.sujal.Ecommerce.DTO.Request.RegisterUserDto;
import com.sujal.Ecommerce.DTO.Response.UserResponse;
import com.sujal.Ecommerce.Entity.User;
import com.sujal.Ecommerce.Enums.Role;
import com.sujal.Ecommerce.Exceptions.UserNotFoundException;
import com.sujal.Ecommerce.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    private BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder(10);

    public User createNewUser(RegisterUserDto user){
        if(user == null){
            throw new RuntimeException("User object cannot be null");
        }

            User newUser = new User();

            Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
            if(existingUser.isPresent()){
                throw new RuntimeException("User already Exist");
            }
             newUser.setUsername(user.getUsername());
             newUser.setPassword(bcrypt.encode(user.getPassword()));
             newUser.setEmail(user.getEmail());
             newUser.setRole(List.of(Role.Buyer));

            return userRepository.save(newUser);

    }

    public User saveExistingUser(User user){
        return userRepository.save(user);
    }

    public List<UserResponse> getAllUser(){
        List<User> existingUser = userRepository.findAll();
        List<UserResponse> userResponse = new ArrayList<>(existingUser.stream()
                .map(user -> modelMapper.map(user, UserResponse.class)).toList()
        );
        return userResponse;
    }

    public User getUserById(Long userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new UserNotFoundException();
        }

        return user.get();
    }

    public Optional<User> getUserFromUsername(String username){
        return userRepository.findByUsername(username);
    }

    public List<UserResponse> getUserByRole(String role){
        Optional<List<User>> fetchedEntity = userRepository.findByRoleIn(List.of(role.toUpperCase()));
        if(fetchedEntity.isEmpty()){
            throw new RuntimeException("User having role " + role + "is not exist.");
        }

        //extracting user from entity
        List<User> userEntity = fetchedEntity.get();

        return new ArrayList<UserResponse>(
                userEntity.
                        stream()
                        .map(user -> modelMapper.map(user, UserResponse.class)).toList()
        );

    }

}
