package com.eventbooking.exception;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ErrorDetails {

	 private int statusCode;
	    private String message;
	    private String path;
	    private LocalDateTime timestamp;

	    public ErrorDetails(int statusCode, String message, String path) {
	        this.statusCode = statusCode;
	        this.message = message;
	        this.path = path;
	        this.timestamp = LocalDateTime.now();
	    }

}
