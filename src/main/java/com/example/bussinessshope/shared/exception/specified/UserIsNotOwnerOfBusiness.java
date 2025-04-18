package com.example.bussinessshope.shared.exception.specified;

public class UserIsNotOwnerOfBusiness extends RuntimeException {
    public UserIsNotOwnerOfBusiness(String message) {
        super(message);
    }
}
