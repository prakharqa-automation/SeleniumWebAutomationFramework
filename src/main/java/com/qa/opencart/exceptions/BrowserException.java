package com.qa.opencart.exceptions;

@SuppressWarnings("serial")
public class BrowserException extends RuntimeException{
    public BrowserException (String message){
        super(message);
    }
}
