package com.pharmacy.exceptions;

public class UsernameNotFoundException extends RuntimeException{

	public UsernameNotFoundException(String message) {
		super(message);
	}
	
}
