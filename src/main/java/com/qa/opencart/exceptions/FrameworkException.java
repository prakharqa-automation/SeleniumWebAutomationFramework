package com.qa.opencart.exceptions;

@SuppressWarnings("serial")
public class FrameworkException extends RuntimeException{
    public FrameworkException(String message){
        super(message);
    }
}
