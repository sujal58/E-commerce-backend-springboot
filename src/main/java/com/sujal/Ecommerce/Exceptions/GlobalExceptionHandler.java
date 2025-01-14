package com.sujal.Ecommerce.Exceptions;

import com.sujal.Ecommerce.DTO.Response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiResponse> ProductNotFoundExceptionHandler(ProductNotFoundException e, HttpServletRequest request){
        return new ResponseEntity<>(ApiResponse.error(HttpStatus.NOT_FOUND,"Product search failed!!", e.getMessage(), request.getRequestURI()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResouceNotFound.class)
    public ResponseEntity<ApiResponse> ResourceNotFoundExceptionHandler(ResouceNotFound e, HttpServletRequest request){

        return new ResponseEntity<>(ApiResponse.error(HttpStatus.NOT_FOUND,"Resource search failed!!", e.getMessage(), request.getRequestURI()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse> UserNotFoundExceptionHandler(UserNotFoundException e, HttpServletRequest request){

        return new ResponseEntity<>(ApiResponse.error(HttpStatus.NOT_FOUND,"User search failed!!", e.getMessage(), request.getRequestURI()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse> CustomExceptionHandler(CustomException e, HttpServletRequest request){
        return new ResponseEntity<>(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Error", e.getMessage(), request.getRequestURI()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({InvalidArgumentException.class, MissingServletRequestParameterException.class})
    public ResponseEntity<ApiResponse> IllegalArgumentsExceptionHandler(Exception e, HttpServletRequest request){
        return new ResponseEntity<>(ApiResponse.error(HttpStatus.BAD_REQUEST, "Illegal Argument passed", e.getMessage(), request.getRequestURI()), HttpStatus.BAD_REQUEST);
    }

}
