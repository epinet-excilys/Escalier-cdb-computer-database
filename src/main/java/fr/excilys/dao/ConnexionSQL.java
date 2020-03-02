package fr.excilys.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnexionSQL {

	private static volatile ConnexionSQL instance = null;
	
	private static HikariConfig config;
	private static HikariDataSource ds;

	private static final String CONFIGURATION_LOCATION = "/database.properties";

	private static final String CONNECTION_LOG = "L'ouverture de connexion a echoué";

	public static Logger LOGGER = LoggerFactory.getLogger(ConnexionSQL.class);
	
	private ConnexionSQL() {
		super();

	}

	public final static ConnexionSQL getInstance() {

		if (ConnexionSQL.instance == null) {

			synchronized (ConnexionSQL.class) {
				if (ConnexionSQL.instance == null) {
					ConnexionSQL.instance = new ConnexionSQL();
				}
			}
		}

		return ConnexionSQL.instance;
	}

	public Connection getConn() {


		
		config = new HikariConfig(CONFIGURATION_LOCATION);
		ds = new HikariDataSource( config );
		
	
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LOGGER.error(CONNECTION_LOG + e.getMessage());
		}
		return null;


	}

}
