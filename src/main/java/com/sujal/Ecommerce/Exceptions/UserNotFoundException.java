package com.sujal.Ecommerce.Exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(){
        super("User doesnot exist");
    }
}
