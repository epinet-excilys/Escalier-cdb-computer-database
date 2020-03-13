package fr.excilys.dao;

public enum EnumSQLCommand {
	CREATE_STATEMENT("INSERT INTO computer(name, introduced, discontinued, company_id) " 
					+ "VALUES(:name, :introduced, :discontinued, :company.id);"),
	UPDATE_STATEMENT("UPDATE computer set name=:name, introduced=:introduced , discontinued=:discontinued, company_id=:company.id where id=:id;"),
	DELETE_STATEMENT("DELETE from computer where id=:id;"),
	DELETE_STATEMENT_GROUP("DELETE from computer where id IN(:idList)"),
	GET_STATEMENT(
			"SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name"
					+ " FROM computer  LEFT JOIN company ON company_id = company.id WHERE computer.id = :id;"),
	GET_ALL_STATEMENT(
			"SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name "
					+ "FROM computer LEFT JOIN company ON company_id = company.id ;"),
	GET_ALL_PAGINATE_STATEMENT(
			"SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name "
					+ "FROM computer LEFT JOIN company ON company_id = company.id LIMIT :offset, :pageSize;"),
	GET_ALL_PAGINATE_ORDER_LIKE_NAME_STATEMENT(
			"SELECT computer.id, computer.name, computer.introduced , computer.discontinued , company_id, company.name "
					+ "FROM computer LEFT JOIN company ON company_id = company.id WHERE computer.name LIKE :search LIMIT  :offset, :pageSize;"),
	GET_ALL_PAGINATE_ORDER_BY_STATEMENT(
			"SELECT computer.id, computer.name, computer.introduced , computer.discontinued , company_id, company.name "
					+ "FROM computer LEFT JOIN company ON company_id = company.id ORDER BY "),
	GET_NB_ROW_STATEMENT("SELECT COUNT(*) as \"Rows\" FROM computer;"),
	GET_NB_ROW_LIKE_STATEMENT("SELECT COUNT(*) as \"Rows\" FROM computer WHERE computer.name LIKE :search ;"),
	GET_STATEMENT_COMPANY("SELECT company.id, company.name FROM company where id=:idCompany"),
	GET_ALL_STATEMENT_COMPANY("SELECT company.id, company.name FROM company"),
	GET_NB_ROW_STATEMENT_COMPANY("SELECT COUNT(*) as \"Rows\" FROM company;"),
	DELETE_STATEMENT_COMPANY("DELETE from company where id=:idCompany;"),
	DELETE_STATEMENT_COMPUTER_WHERE_COMPANY(" DELETE FROM computer WHERE company_id = :idCompany;");
	
	
	private String message; 
	
	EnumSQLCommand(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	

}
