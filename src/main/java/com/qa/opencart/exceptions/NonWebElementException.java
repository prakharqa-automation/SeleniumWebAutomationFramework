package com.qa.opencart.exceptions;


@SuppressWarnings("serial")
public class NonWebElementException extends RuntimeException{
	
	public NonWebElementException(String message) {
		super(message);
	}
	
}
