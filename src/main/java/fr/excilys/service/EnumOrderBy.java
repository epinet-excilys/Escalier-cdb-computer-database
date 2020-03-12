package fr.excilys.service;

public enum EnumOrderBy {
	COMPUTER_DAO("computer.name"),
	INTRODUCED_DATE_DAO("computer.introduced"),
	DISCONTINUED_DATE_DAO("computer.discontinued"),
	COMPANY_DAO("company.name");
	
	private String message; 
	
	EnumOrderBy(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}

}
