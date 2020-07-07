package com.eatza.deliveryservice.exception;

public class InvalidTokenException extends RuntimeException {
	
	/**
	 * 
	 */
	 private String errorCode;
	private static final long serialVersionUID = 1L;
	public InvalidTokenException() {
		super();
	}
	public InvalidTokenException(String msg) {
		super(msg);
	}

}
