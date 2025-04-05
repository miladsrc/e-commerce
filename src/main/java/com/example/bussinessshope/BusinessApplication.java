package com.example.bussinessshope;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


//@EntityScan(basePackages = {"com.example.bussinessshope.user",
//        "com.example.bussinessshope.business",
//        "com.example.bussinessshope,order",
//        "com.example.bussinessshope.product",})
@SpringBootApplication
public class BusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinessApplication.class, args);
    }

}
