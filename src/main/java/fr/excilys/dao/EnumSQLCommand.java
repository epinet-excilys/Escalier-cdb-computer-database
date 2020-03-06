package fr.excilys.dao;

public enum EnumSQLCommand {
	CREATE_STATEMENT("INSERT INTO computer(name, introduced, discontinued, company_id) " + "VALUES(?, ?, ?, ?);"),
	UPDATE_STATEMENT("UPDATE computer set name=?, introduced=? , discontinued=?, company_id=? where id=?;"),
	DELETE_STATEMENT("DELETE from computer where id=?;"),
	GET_STATEMENT(
			"SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name"
					+ " FROM computer  LEFT JOIN company ON company_id = company.id WHERE computer.id = ?;"),
	GET_ALL_STATEMENT(
			"SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name "
					+ "FROM computer LEFT JOIN company ON company_id = company.id ;"),
	GET_ALL_PAGINATE_STATEMENT(
			"SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name "
					+ "FROM computer LEFT JOIN company ON company_id = company.id LIMIT ?, ?;"),
	GET_ALL_PAGINATE_ORDER_LIKE_NAME_STATEMENT(
			"SELECT computer.id, computer.name, computer.introduced , computer.discontinued , company_id, company.name "
					+ "FROM computer LEFT JOIN company ON company_id = company.id WHERE computer.name LIKE ? LIMIT ?,?;"),
	GET_ALL_PAGINATE_ORDER_BY_NAME_STATEMENT(
			"SELECT computer.id, computer.name, computer.introduced , computer.discontinued , company_id, company.name "
					+ "FROM computer LEFT JOIN company ON company_id = company.id ORDER BY computer.name LIMIT ?,?;"),
	GET_NB_ROW_STATEMENT("SELECT COUNT(*) as \"Rows\" FROM computer;"),
	GET_NB_ROW_LIKE_STATEMENT("SELECT COUNT(*) as \"Rows\" FROM computer WHERE computer.name LIKE ?;"),
	GET_STATEMENT_COMPANY("SELECT company.id, company.name FROM company where id=?"),
	GET_ALL_STATEMENT_COMPANY("SELECT company.id, company.name FROM company"),
	GET_NB_ROW_STATEMENT_COMPANY("SELECT COUNT(*) as \"Rows\" FROM company;");
	
	
	private String message; 
	
	EnumSQLCommand(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	

}
