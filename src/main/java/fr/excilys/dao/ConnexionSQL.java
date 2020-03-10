package fr.excilys.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import fr.excilys.exception.DatabaseManipulationException;

//@Configuration
//@PropertySource("classpath:/datasource.properties")
public class ConnexionSQL {

	private static volatile ConnexionSQL instance = null;
//	private static HikariConfig config;
//	private static HikariDataSource datasource;
	private static final String CONFIGURATION_LOCATION = "/database.properties";
	private static final String CONNECTION_LOG = "L'ouverture de connexion a echoué";
	public static Logger LOGGER = LoggerFactory.getLogger(ConnexionSQL.class);

	private ConnexionSQL() {
	}
	
	public static DataSource getHikariDataSource() {
		HikariConfig config = new HikariConfig(CONFIGURATION_LOCATION);
		HikariDataSource dataSource = new HikariDataSource(config);
		return dataSource;
	}
	

	public final static ConnexionSQL getInstance() {

		if (ConnexionSQL.instance == null) {
			if (ConnexionSQL.instance == null) {
				ConnexionSQL.instance = new ConnexionSQL();
			}
		}
		return ConnexionSQL.instance;
	}


	public static Connection getConn() {
		try {
			return getHikariDataSource().getConnection();
		} catch (SQLException sqlException) {
			LOGGER.error(CONNECTION_LOG + sqlException.getMessage());
		}
		throw new DatabaseManipulationException();

	}

}
