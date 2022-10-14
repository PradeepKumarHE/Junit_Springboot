package com.pradeep.exceptions;

public class ResourceExistsException extends RuntimeException{

    public ResourceExistsException(String mesaage){
        super(mesaage);
    }
    public ResourceExistsException(String mesaage, Throwable cause){
        super(mesaage,cause);
    }
}
