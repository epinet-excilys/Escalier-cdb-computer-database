package fr.excilys.validator;

import java.time.LocalDate;

import fr.excilys.model.Computer;
import fr.excilys.service.CompanyDAOService;
import fr.excilys.service.ComputerDAOService;

public class Validator {
	
	private static volatile Validator instance = null;
	private static final LocalDate LOCAL_DATE_MAX_BDD = LocalDate.of(1971, 01, 01);
	
	
	public final static Validator getInstance() {

		if (Validator.instance == null) {

			synchronized (Validator.class) {
				if (Validator.instance == null) {
					Validator.instance = new Validator();
				}
			}
		}

		return Validator.instance;
	}
	
	public boolean Validation(Computer computer) {
		return  (!computer.getName().isEmpty())
				&& isDateOk(computer.getIntroducedDate(),computer.getDiscontinuedDate());
		
	}
	

	private boolean isDateOk (LocalDate introducedDate, LocalDate discontinuedDate) {
		return (((introducedDate!=null)&&(discontinuedDate)!=null) 
				&&(discontinuedDate.isAfter(LOCAL_DATE_MAX_BDD)&&(discontinuedDate.isAfter(LOCAL_DATE_MAX_BDD))) 
				&&(discontinuedDate.isAfter(introducedDate)))
				||( (introducedDate==null) && ((discontinuedDate.isAfter(LOCAL_DATE_MAX_BDD))) )
				||( (discontinuedDate==null) && ((introducedDate.isAfter(LOCAL_DATE_MAX_BDD))) )
				||( (discontinuedDate==null) && (introducedDate==null) );
		
	}
	

}
