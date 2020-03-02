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



public class ConnexionSQL {

	private static volatile ConnexionSQL instance = null;

	private static Properties connectionProperties;
	private static String url;
	private static String user;
	private static String password;
	private static String driver;
	private static final String CONFIGURATION_LOCATION = "database.properties";

	private static final String IOE_LOG = "Le chargement des proprieté";
	private static final String CONNECTION_LOG = "L'ouverture de connexion a echoué";
	private static final String CLASS_NOT_FOUND_LOG = "La classe n'est pas trouver ";

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

		connectionProperties = new Properties();

		try {
			connectionProperties
					.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIGURATION_LOCATION));

			driver = connectionProperties.getProperty("driver");
			url = connectionProperties.getProperty("url");
			user = connectionProperties.getProperty("user");
			password = connectionProperties.getProperty("password");

			Class.forName(driver);

			return DriverManager.getConnection(url, user, password);

		} catch (IOException e1) {
			LOGGER.error(IOE_LOG + e1.getMessage());
		} catch (SQLException e2) {
			LOGGER.error(CONNECTION_LOG + e2.getMessage());
		} catch (ClassNotFoundException e3) {
			LOGGER.error(CLASS_NOT_FOUND_LOG + e3.getMessage());
		}

		// TODO Hikari va permettre d'enlever le return null;
		{
			System.exit(0);

			return null;

		}
	}

}
