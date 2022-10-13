package com.pradeep.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String mesaage){
        super(mesaage);
    }
    public ResourceNotFoundException(String mesaage,Throwable cause){
        super(mesaage,cause);
    }
}
