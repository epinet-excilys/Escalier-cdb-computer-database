package fr.excilys.config;

public enum EnumProperties {
	PROPERTIES_DRIVER("dataSource.driverClassName"),
	PROPERTIES_URL("dataSource.jdbcUrl"),
	PROPERTIES_USER("dataSource.username"),
	PROPERTIES_PASSWORD("dataSource.password");

	private String message;
	
	EnumProperties(String string) {
		this.message = string;
	}
	
	public String getMessage() {
		return this.message;
	}

}
