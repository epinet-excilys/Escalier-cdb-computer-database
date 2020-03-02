package fr.excilys.client;

public  enum  EnumMenu {
	  DISPLAYALLCOMPUTER,
	  DISPLAYCOMPUTER,
	  CREATE,
	  DELETE,
	  UPDATE,
	  DISPLAYCOMPANY,
	  EXIT;
	

	  
	public static EnumMenu valueOf(int action) {
		
		
		switch(action) {
		
		case 0 :
			return DISPLAYALLCOMPUTER; 
		case 1 :
			return DISPLAYCOMPUTER; 
		case 2 :
			return CREATE; 
		case 3 :
			return DELETE; 
		case 4 :
			return UPDATE; 
		case 5 :
			return DISPLAYCOMPANY;
		case 6 :
			return EXIT;
		default :
			return EXIT;
		}
		
	}	  
	
	   
}
