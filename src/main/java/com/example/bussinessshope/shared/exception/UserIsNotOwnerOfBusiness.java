package com.example.bussinessshope.shared.exception;

public class UserIsNotOwnerOfBusiness extends RuntimeException {
    public UserIsNotOwnerOfBusiness(String message) {
        super(message);
    }
}
