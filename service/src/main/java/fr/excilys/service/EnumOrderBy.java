package fr.excilys.service;

public enum EnumOrderBy {
	COMPUTER_DAO("computer.name NULLS LAST"),
	INTRODUCED_DATE_DAO("computer.introducedDate NULLS LAST"),
	DISCONTINUED_DATE_DAO("computer.discontinuedDate NULLS LAST"),
	COMPANY_DAO("company.name NULLS LAST");
	
	private String message;

	EnumOrderBy(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

}
