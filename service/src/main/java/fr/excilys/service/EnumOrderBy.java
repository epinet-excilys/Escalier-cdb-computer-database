package fr.excilys.service;

public enum EnumOrderBy {
	COMPUTER_DAO("computer.name"),
	INTRODUCED_DATE_DAO("computer.introducedDate"),
	DISCONTINUED_DATE_DAO("computer.discontinuedDate"),
	COMPANY_DAO("company.name");
	
	private String message;

	EnumOrderBy(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

}
