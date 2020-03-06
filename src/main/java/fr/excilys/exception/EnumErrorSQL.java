package fr.excilys.exception;

public enum EnumErrorSQL {
	
	BDD_ACCESS_LOG("Impossible de se connecter à la  BDD niveau DAO"),
	BDD_NULL_OBJECT_LOG("Tentative de manipulation d'un objet null"),
	SQL_EXCEPTION_LOG("La manipulation du ResultSet a provoquer une error");

	private String message; 
	
	EnumErrorSQL(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
}
