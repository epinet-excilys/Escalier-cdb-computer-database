package fr.excilys.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;

import fr.excilys.exception.EnumErrorSQL;
import fr.excilys.mapper.CompanyMapper;
import fr.excilys.model.Company;

public final class CompanyDAO {

	private static volatile CompanyDAO instance = null;

	public static Logger LOGGER = LoggerFactory.getLogger(ConnexionSQL.class);

	private CompanyDAO() {
	}

	public final static CompanyDAO getInstance() {
		if (CompanyDAO.instance == null) {
			if (CompanyDAO.instance == null) {
				CompanyDAO.instance = new CompanyDAO();
			}
		}
		
		return CompanyDAO.instance;
	
	}
	

	public Optional<Company> findByID(int idSearch) {
		Company company = new Company.Builder().build();
		try (Connection connect = ConnexionSQL.getInstance().getConn();
				PreparedStatement stmt = connect.prepareStatement(EnumSQLCommand.GET_STATEMENT_COMPANY.getMessage());
				ResultSet result = stmt.executeQuery();) {
			stmt.setInt(1, idSearch);
			if (result.first()) {
				company = CompanyMapper.getInstance().getCompanyFromResultSet(result);
			}
		} catch (SQLException sqlException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + sqlException.getMessage());
		}
		return Optional.ofNullable(company);
	}

	public List<Company> findAll() {
		List<Company> listCompany = new ArrayList<>();
		Company company = new Company.Builder().build();
		try (Connection connect = ConnexionSQL.getInstance().getConn();
				PreparedStatement stmt = connect
						.prepareStatement(EnumSQLCommand.GET_ALL_STATEMENT_COMPANY.getMessage());) {
			try (ResultSet result = stmt.executeQuery()) {
				while (result.next()) {
					company = CompanyMapper.getInstance().getCompanyFromResultSet(result);

					listCompany.add(company);
				}
			}
		} catch (SQLException sqlException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + sqlException.getMessage());
		}
		return listCompany;
	}

	public int getNbRow() {
		int nbRow = 0;
		try (Connection connect = ConnexionSQL.getInstance().getConn();
				PreparedStatement stmt = connect
						.prepareStatement(EnumSQLCommand.GET_NB_ROW_STATEMENT_COMPANY.getMessage());) {

			try (ResultSet result = stmt.executeQuery()) {

				if (result.first()) {
					nbRow = result.getInt("Rows");

				}
			}

		} catch (SQLException sqlException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + sqlException.getMessage());
		}

		return nbRow;

	}

}
