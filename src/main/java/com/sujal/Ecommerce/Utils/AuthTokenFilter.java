package com.sujal.Ecommerce.Utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String jwt = jwtUtil.getTokenFromHeader(request);

            if(jwt != null && jwtUtil.validateToken(jwt)){
                String username = jwtUtil.getUsernameFromToken(jwt);

                System.out.println(username);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                System.out.println("initial check");
                System.out.println(userDetails.getUsername());
                System.out.println("final check");
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());


                System.out.println("AuthTokenFilter");
                //-----------------------------------------debugging purpose--------------------------------------------------------
                System.out.println(authentication);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //-----------------------------------------debugging purpose--------------------------------------------------------
                System.out.println(authentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
