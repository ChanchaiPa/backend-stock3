package com.demo.stock.exception;

public class UserDuplicateException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UserDuplicateException(String username) {
		super("Username " + username + " already exist.");
	}

}
