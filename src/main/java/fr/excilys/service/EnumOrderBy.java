package fr.excilys.service;

public enum EnumOrderBy {
	COMPUTER_DAO("name"),
	INTRODUCED_DATE_DAO("introduced"),
	DISCONTINUED_DATE_DAO("discontinued"),
	COMPANY_DAO("company");
	
	private String message; 
	
	EnumOrderBy(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}

}
