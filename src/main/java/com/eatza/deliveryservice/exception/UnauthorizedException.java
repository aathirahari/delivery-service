package com.eatza.deliveryservice.exception;

public class UnauthorizedException extends Exception {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnauthorizedException() {
		super();
	}
	
	public UnauthorizedException(String msg) {
		super(msg);
	}

}
