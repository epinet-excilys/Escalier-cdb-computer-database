package fr.excilys.exception;

@SuppressWarnings("serial")
public class DatabaseManipulationException extends RuntimeException{
	
	public DatabaseManipulationException() {
	}
	
	public DatabaseManipulationException(String errorMessage) {
		super(errorMessage);
	}
	
}
