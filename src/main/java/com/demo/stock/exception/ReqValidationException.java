package com.demo.stock.exception;

public class ReqValidationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ReqValidationException(String message) {
		super("Validate Fail... field " + message);
	}

}
