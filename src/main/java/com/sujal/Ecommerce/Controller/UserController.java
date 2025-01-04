package com.sujal.Ecommerce.Controller;

import com.sujal.Ecommerce.DTO.Request.LoginRequestDto;
import com.sujal.Ecommerce.DTO.Request.RegisterUserDto;
import com.sujal.Ecommerce.DTO.Response.LoginResponseDto;
import com.sujal.Ecommerce.Entity.UserEntity;
import com.sujal.Ecommerce.Service.CustomUserDetailService;
import com.sujal.Ecommerce.Service.UserService;
import com.sujal.Ecommerce.Utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;



    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid  @RequestBody RegisterUserDto user) {

        UserEntity response;
        try{
             response = userService.createNewUser(user);
        }catch(Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginDto){
         Authentication auth;
        try{
             auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(auth);

            //extracting autheticated user from the security context holder (principal = autheticated user)

            UserDetails userDetails = (UserDetails) auth.getPrincipal();

            String jwtToken = jwtUtil.generateToken(userDetails.getUsername());

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(role -> role.getAuthority())
                    .toList();

            LoginResponseDto response = new LoginResponseDto(jwtToken, userDetails.getUsername(), roles);

            return ResponseEntity.ok(response);

        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

}
