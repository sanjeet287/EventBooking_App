package com.eventbooking.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	 @ExceptionHandler(ResourceNotFoundException.class)
	    public ResponseEntity<ErrorDetails> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request){
	        ErrorDetails errorDetails = new ErrorDetails(
	                HttpStatus.NOT_FOUND.value(),
	                ex.getMessage(),
	                request.getDescription(false)
	        );
	        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	    }

	    @ExceptionHandler(BadRequestException.class)
	    public ResponseEntity<ErrorDetails> handleBadRequest(BadRequestException ex, WebRequest request){
	        ErrorDetails errorDetails = new ErrorDetails(
	                HttpStatus.BAD_REQUEST.value(),
	                ex.getMessage(),
	                request.getDescription(false)
	        );
	        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	    }

	    // Handle validation errors (DTO validation)
	    @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex){
	        Map<String, String> errors = new HashMap<>();

	        ex.getBindingResult().getFieldErrors().forEach(error -> {
	            errors.put(error.getField(), error.getDefaultMessage());
	        });

	        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	    }

	    // Handle any other exception (fallback)
	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request){
	        ErrorDetails errorDetails = new ErrorDetails(
	                HttpStatus.INTERNAL_SERVER_ERROR.value(),
	                ex.getMessage(),
	                request.getDescription(false)
	        );
	        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

}
