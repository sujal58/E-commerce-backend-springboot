package com.sujal.Ecommerce.Exceptions;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(Long id){
        super("Product having id:" + id + " doesn't exist.");
    }
}

