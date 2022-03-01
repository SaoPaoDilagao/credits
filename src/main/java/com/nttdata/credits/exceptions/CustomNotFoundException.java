package com.nttdata.credits.exceptions;

public class CustomNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomNotFoundException(String message) {
        super(message);
    }
}
