package br.com.pelegrino.food.application;

@SuppressWarnings("serial")
public class ValidationException extends Exception {

	public ValidationException(String message) {
		super(message);
	}
	
}
