package com.sujal.Ecommerce.Exceptions;

public class ResourceNotFoundExcption extends RuntimeException{

    public ResourceNotFoundExcption(String resource, Long id){
        super(resource + " having id:" + id + " doesnot exist.");
    }
}
