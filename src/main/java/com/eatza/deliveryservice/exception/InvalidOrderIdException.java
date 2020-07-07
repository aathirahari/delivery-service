package com.eatza.deliveryservice.exception;

import java.util.function.Supplier;

public class InvalidOrderIdException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4501679791293596508L;



	public InvalidOrderIdException() {
		super();
		 
	}

	

	public InvalidOrderIdException(String message) {
		super(message);
		 
	}



	public InvalidOrderIdException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	


}
