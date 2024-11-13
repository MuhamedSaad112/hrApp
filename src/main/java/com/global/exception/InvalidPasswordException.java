package com.global.exception;

public class InvalidPasswordException extends RuntimeException {
	
	  public InvalidPasswordException() {
	        super("Incorrect password");
	    }

}
