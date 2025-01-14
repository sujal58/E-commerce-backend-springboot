package com.sujal.Ecommerce.Exceptions;

public class CustomException extends RuntimeException{

    public CustomException(String errorMessage){
        super(errorMessage);
    }
}
