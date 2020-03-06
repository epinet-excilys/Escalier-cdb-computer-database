package fr.excilys.exception;

public class DatabaseManipulationException extends RuntimeException{
	
	public DatabaseManipulationException() {
		super();
	}
	
	public DatabaseManipulationException(String errorMessage) {
		super(errorMessage);
	}
	
}
