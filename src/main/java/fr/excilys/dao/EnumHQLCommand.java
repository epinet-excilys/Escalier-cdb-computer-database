package fr.excilys.dao;

public enum EnumHQLCommand {
	UPDATE_STATEMENT("UPDATE Computer set name=:name, introduced=:introduced , discontinued=:discontinued, company_id=:company.id where id=:id;"),
	DELETE_STATEMENT("DELETE from Computer computer where id=:id;"),
	DELETE_STATEMENT_GROUP("DELETE from Computer computer where id IN(:idList);"),
	GET_STATEMENT("SELECT Computer from Computer computer  LEFT JOIN computer.company WHERE computer.id = :id;"),
	GET_ALL_STATEMENT("SELECT Computer computer FROM Computer computer LEFT JOIN computer.company;"),
	GET_ALL_PAGINATE_ORDER_LIKE_NAME_STATEMENT(
			"SELECT computer FROM Computer computer LEFT JOIN computer.company WHERE computer.name LIKE :search;"),
	GET_ALL_PAGINATE_ORDER_BY_STATEMENT(
			"SELECT computer FROM computer computer LEFT JOIN computer.company ON company_id = company.id ORDER BY "),
	GET_NB_ROW_STATEMENT("SELECT count(computer.id) FROM Computer computer;"),
	GET_NB_ROW_LIKE_STATEMENT("SELECT count(computer.id) FROM Computer computer WHERE computer.name LIKE :search;"),
	GET_ALL_STATEMENT_COMPANY("SELECT Company FROM Company company "),
	DELETE_STATEMENT_COMPANY("DELETE from Company company where company. id=:idCompany;"),
	DELETE_STATEMENT_COMPUTER_WHERE_COMPANY(" DELETE FROM computer computer WHERE computer.company_id = :idCompany;"),
	GET_STATEMENT_COMPANY("SELECT Company FROM Company company where company.id = :idCompany"),
	GET_NB_ROW_STATEMENT_COMPANY("SELECT count(company.id) FROM Company company;");
	
	
	private String message; 
	
	EnumHQLCommand(String message) {
		
		this.message = message;
	}
	
	public String getMessage() {
		
		return this.message;
	}
	
	

}
