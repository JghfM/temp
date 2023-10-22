package com.example.demo.exception;

public class ElementNotFoundException extends RuntimeException {

    public ElementNotFoundException(String message){
        super(message);
    }

}