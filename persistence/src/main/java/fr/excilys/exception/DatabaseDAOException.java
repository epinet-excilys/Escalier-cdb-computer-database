package fr.excilys.exception;

@SuppressWarnings("serial")
public class DatabaseDAOException extends DatabaseManipulationException{
	
	public DatabaseDAOException() {
		
	}
	
	public DatabaseDAOException(String errorMessage) {
		super(errorMessage);
	}

}
