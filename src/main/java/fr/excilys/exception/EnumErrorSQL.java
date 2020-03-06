package fr.excilys.exception;

public enum EnumErrorSQL {
	BDD_ACCESS_LOG("Impossible de se connecter à la  BDD niveau DAO"),
	BDD_NULL_OBJECT_LOG("Tentative de manipulation d'un objet null");

	
	private String message; 
	
	EnumErrorSQL(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
}
