package fr.excilys.validator;

import java.time.LocalDate;

import fr.excilys.model.Computer;

public class Validator {
	
	private static volatile Validator instance = null;
	private static final LocalDate LOCAL_DATE_MAX_BDD = LocalDate.of(1971, 01, 01);
	
	public final static Validator getInstance() {

		if (Validator.instance == null) {
				if (Validator.instance == null) {
					Validator.instance = new Validator();
				}
		}

		return Validator.instance;
	}
	
	public boolean Validation(Computer computer) {
		return  isNameOk(computer) && 
				isDateOk(computer.getIntroducedDate(),computer.getDiscontinuedDate());
		
	}
	
	public static boolean isDateOk (LocalDate introducedDate, LocalDate discontinuedDate) {
		
		return ( bothOkAndNotNull(introducedDate,discontinuedDate)
				
				|| ( bothNull(introducedDate, discontinuedDate) )
				
				|| ( introducedNullAndDiscontinuedOk(introducedDate, discontinuedDate) )
				
				|| ( discontinuedNullAndIntroducedOk(introducedDate, discontinuedDate) )) ;
				
		
	}
	
	public static boolean isNameOk (Computer computer) {
		return(!computer.getName().isEmpty())&&(!computer.getName().isBlank());
	}
	
	private static boolean bothOkAndNotNull(LocalDate introducedDate, LocalDate discontinuedDate) {
		return((introducedDate!=null) && (discontinuedDate)!=null) && (introducedDate.isAfter(LOCAL_DATE_MAX_BDD)
				&& (discontinuedDate.isAfter(LOCAL_DATE_MAX_BDD))) && (discontinuedDate.isAfter(introducedDate));
	}
	
	private static boolean bothNull(LocalDate introducedDate, LocalDate discontinuedDate) {
		return ((discontinuedDate==null) && (introducedDate==null));
	}
	
	private static boolean introducedNullAndDiscontinuedOk(LocalDate introducedDate, LocalDate discontinuedDate) {
		return (introducedDate==null) && ((discontinuedDate.isAfter(LOCAL_DATE_MAX_BDD)));
	}
	
	private static boolean discontinuedNullAndIntroducedOk(LocalDate introducedDate, LocalDate discontinuedDate) {
		return (discontinuedDate==null) && ((introducedDate.isAfter(LOCAL_DATE_MAX_BDD)));
	}
}
