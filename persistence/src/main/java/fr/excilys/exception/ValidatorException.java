package fr.excilys.exception;

public class ValidatorException extends RuntimeException{
	
	public ValidatorException(String errorValidationMessage) {
		super(errorValidationMessage);
	}
	
}
