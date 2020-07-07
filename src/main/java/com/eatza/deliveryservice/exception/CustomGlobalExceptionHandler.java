package com.eatza.deliveryservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import io.jsonwebtoken.ExpiredJwtException;



@RestControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	private static final Logger logger = LoggerFactory.getLogger(CustomGlobalExceptionHandler.class);

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<Object> exception(UnauthorizedException exception) {
		logger.debug("Handling UnauthorizedException");
		 return new ResponseEntity<>("Invalid Credentials", HttpStatus.UNAUTHORIZED);
	}
	@ExceptionHandler(DeliveryPersonException.class)
	public ResponseEntity<Object> exception(DeliveryPersonException exception) {
		logger.debug("Handling DeliveryPersonException");
		logger.debug(exception.getMessage());
		 return new ResponseEntity<>(exception.getMessage(), HttpStatus.OK);
	}
	@ExceptionHandler(InvalidOrderIdException.class)
	public ResponseEntity<Object> exception(InvalidOrderIdException exception) {
		logger.debug("Handling InvalidOrderIdException");
		logger.debug(exception.getMessage());
		 return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	}

	

	
	
	
}
