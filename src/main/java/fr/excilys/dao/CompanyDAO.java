package fr.excilys.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

import fr.excilys.exception.DatabaseDAOException;
import fr.excilys.exception.EnumErrorSQL;
import fr.excilys.mapper.CompanyMapper;
import fr.excilys.model.Company;

@Repository
public final class CompanyDAO {

	public static Logger LOGGER = LoggerFactory.getLogger(ConnexionSQL.class);
	private ConnexionSQL connectionToGetAsAutoWired;
	private CompanyMapper companyMapper;

	public CompanyDAO(ConnexionSQL connectionSQL, CompanyMapper companyMapper) {
		this.connectionToGetAsAutoWired = connectionSQL;
		this.companyMapper = companyMapper;
	}

	public void deleteCompany(int iDCompany) {
		
		try (Connection connect = connectionToGetAsAutoWired.getConn();
				PreparedStatement statementComputers = connect.prepareStatement(EnumSQLCommand.DELETE_STATEMENT_COMPUTER_WHERE_COMPANY.getMessage());
				PreparedStatement statementCompany = connect.prepareStatement(EnumSQLCommand.DELETE_STATEMENT_COMPANY.getMessage());){
			
			connect.setAutoCommit(false);
			
			statementComputers.setInt(1, iDCompany);
			statementCompany.setInt(1, iDCompany);
			statementComputers.executeUpdate();
			statementCompany.executeUpdate();
			
			connect.commit();
			connect.setAutoCommit(true);
			
		} catch (SQLException sqlException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + sqlException.getMessage());
			throw new DatabaseDAOException("Delete Company");
		}
		
	}


	public Optional<Company> findByID(int idSearch) {
		
		Optional<Company> optionalCompany = Optional.empty();
		try (Connection connect = connectionToGetAsAutoWired.getConn();
				PreparedStatement stmt = connect.prepareStatement(EnumSQLCommand.GET_STATEMENT_COMPANY.getMessage());
				ResultSet result = setResultSetForID(idSearch, stmt);) {
			if (result.first()) {
				optionalCompany = companyMapper.getCompanyFromResultSet(result);
			}

			return optionalCompany;

		} catch (SQLException sqlException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + sqlException.getMessage());
			throw new DatabaseDAOException("FindById in Company");
		}
	}

	public List<Company> findAll() {
		
		List<Company> listCompany = new ArrayList<>();
		Company company = new Company.Builder().build();
		try (Connection connect = connectionToGetAsAutoWired.getConn();
				PreparedStatement stmt = connect
						.prepareStatement(EnumSQLCommand.GET_ALL_STATEMENT_COMPANY.getMessage());
				ResultSet result = stmt.executeQuery();) {

			if (result.isBeforeFirst()) {
				while (result.next()) {
					company = companyMapper.getCompanyFromResultSet(result).get();
					listCompany.add(company);
				}
			}

			return listCompany;

		} catch (SQLException sqlException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + sqlException.getMessage());
			throw new DatabaseDAOException("findAll in Company");
		}
	}

	public int getNbRow() {

		try (Connection connect = connectionToGetAsAutoWired.getConn();
				PreparedStatement stmt = connect
						.prepareStatement(EnumSQLCommand.GET_NB_ROW_STATEMENT_COMPANY.getMessage());
				ResultSet result = stmt.executeQuery();) {
			if (result.first()) {
				return result.getInt("Rows");

			}
		} catch (SQLException sqlException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + sqlException.getMessage());
		}
		throw new DatabaseDAOException("NbRows in Company");
	}	
	
	private ResultSet setResultSetForID(int id, PreparedStatement stmt) throws SQLException {

		stmt.setInt(1, id);
		ResultSet result = stmt.executeQuery();

		return result;
	}

}
