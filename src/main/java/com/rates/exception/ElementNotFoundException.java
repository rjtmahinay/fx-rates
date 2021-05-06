package com.rates.exception;

/**
 * @author Tristan Mahinay
 */
public class ElementNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -6977198874063419055L;

	public ElementNotFoundException(String message) {
		super(message);
	}
}
