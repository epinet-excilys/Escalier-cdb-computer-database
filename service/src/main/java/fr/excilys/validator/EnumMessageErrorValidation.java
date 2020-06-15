package fr.excilys.validator;

public enum EnumMessageErrorValidation {
	
	SUCCESS_CREATE("Successfull addition to the Database"),
    SUCCESS_DELETE("Successfull deletion from the Database"),
    SUCCESS_UPDATE("Successfull update"),
	ERROR_COMPUTER_ID("The computer id isn't valid, please verify your entry."),
	ERROR_NAME("The name isn't valid, please verify your entry."),
	ERROR_DATE("Dates aren't coherent with one another, please verify your entry."), 
	ERROR_DATE_FORMAT("The Date isn't in a Valid Format, please verify your entry"),
	ERROR_DATE_VALUE("The Date shouldn't be null at this point, contact your administrator"),
	ERROR_COMPANY("The company id isn't valid, please verify your entry."),
	ERROR_PAGINATION("The pagination has encouter a difficulty, please retry");

	private String message;

	EnumMessageErrorValidation(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}

}
