package com.example.bussinessshope.shared.exception;

public class BusinessNotFoundException extends RuntimeException{
    public BusinessNotFoundException(String message){
        super(message);
    }
}
