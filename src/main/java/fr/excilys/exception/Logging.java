package fr.excilys.exception;


import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.excilys.dao.ConnexionSQL;

public class Logging 
{
	public static Logger LOGGER = LoggerFactory.getLogger(ConnexionSQL.class);
	
	public static Logger getLog() {
		PropertyConfigurator.configure(Logging.class.getClassLoader().getResource("log4j.properties"));
		return LOGGER;
	}
	

	
}
