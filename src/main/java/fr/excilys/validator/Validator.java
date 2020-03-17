package fr.excilys.validator;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Component;

import fr.excilys.exception.ValidatorException;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.model.Computer;

@Component
public class Validator {
	
	private ComputerMapper computerMapper;
	
	

	private static final LocalDate LOCAL_DATE_MAX_BDD = LocalDate.of(1971, 01, 01);

	public void validateFields(String computerName, String introduced, String discontinued, String companyId) throws ValidatorException{
		if(!isNameOk(computerName)) {
			throw new ValidatorException(EnumMessageErrorValidation.ERROR_NAME.getMessage());
		}
		LocalDate introducedDate = isStringCorrectDateFormat(introduced);
		LocalDate discontinuedDate = isStringCorrectDateFormat(discontinued);
		isDateOk(introducedDate,discontinuedDate);
		
		
		
		
	}
	
	public boolean ValidateComputer(Computer computer) throws ValidatorException{

		isDateOk(computer.getIntroducedDate(), computer.getDiscontinuedDate());
		return isNameOk(computer.getName());
				

	}
	

	public void isDateOk(LocalDate introducedDate, LocalDate discontinuedDate) {

		if(!((bothOkAndNotNull(introducedDate, discontinuedDate) 
				|| (bothNull(introducedDate, discontinuedDate))
				|| (introducedNullAndDiscontinuedOk(introducedDate, discontinuedDate))
				|| (discontinuedNullAndIntroducedOk(introducedDate, discontinuedDate))))) {
			throw new ValidatorException(EnumMessageErrorValidation.ERROR_DATE.getMessage());
		}
	}

	public boolean isNameOk(String computerName) {
		return (!computerName.isEmpty()) && (!computerName.isBlank());
	}

	private boolean bothOkAndNotNull(LocalDate introducedDate, LocalDate discontinuedDate) {
		return ((introducedDate != null) && (discontinuedDate) != null)
				&& (introducedDate.isAfter(LOCAL_DATE_MAX_BDD) && (discontinuedDate.isAfter(LOCAL_DATE_MAX_BDD)))
				&& (discontinuedDate.isAfter(introducedDate));
	}

	private boolean bothNull(LocalDate introducedDate, LocalDate discontinuedDate) {
		return ((discontinuedDate == null) && (introducedDate == null));
	}

	private boolean introducedNullAndDiscontinuedOk(LocalDate introducedDate, LocalDate discontinuedDate) {
		return (introducedDate == null) && ((discontinuedDate.isAfter(LOCAL_DATE_MAX_BDD)));
	}

	private boolean discontinuedNullAndIntroducedOk(LocalDate introducedDate, LocalDate discontinuedDate) {
		return (discontinuedDate == null) && ((introducedDate.isAfter(LOCAL_DATE_MAX_BDD)));
	}
	
	private LocalDate isStringCorrectDateFormat(String stringDateToValidate) {
		try {
			
		if((!stringDateToValidate.isEmpty()) && (!stringDateToValidate.isBlank())) {
			return computerMapper.fromStringToLocalDate(stringDateToValidate);
		}
		}catch(DateTimeException dateTimeException) {
			throw new ValidatorException(EnumMessageErrorValidation.ERROR_DATE_FORMAT.getMessage());
		}
		throw new ValidatorException(EnumMessageErrorValidation.ERROR_DATE_VALUE.getMessage());
	}


}
