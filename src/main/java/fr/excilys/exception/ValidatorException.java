package fr.excilys.exception;

public class ValidatorException extends DatabaseManipulationException{
	
	public ValidatorException() {
		super();
	}
	
	public ValidatorException(String errorMessage) {
		super(errorMessage);
	}
	
}
