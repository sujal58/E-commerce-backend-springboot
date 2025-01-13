package com.sujal.Ecommerce.Exceptions;

public class ResouceNotFound extends RuntimeException{

    public ResouceNotFound(String resourceName){
        super(resourceName +" Doesnot exist!!!");
    }
}
