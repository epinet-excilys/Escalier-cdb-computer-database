package fr.excilys.dao;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.excilys.exception.DatabaseDAOException;
import fr.excilys.exception.EnumErrorSQL;
import fr.excilys.mapper.CompanyMapper;
import fr.excilys.model.Company;

@Repository
public final class CompanyDAO {

	public static Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);
	private CompanyMapper companyMapper;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public CompanyDAO(DataSource datasource, CompanyMapper companyMapper) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(datasource);
		this.companyMapper = companyMapper;
	}

	@Transactional 
	public void deleteCompany(int iDCompany) {
		
		try {
			MapSqlParameterSource parameterMap = new MapSqlParameterSource().addValue("idCompany", iDCompany);
			namedParameterJdbcTemplate.update(EnumSQLCommand.DELETE_STATEMENT_COMPUTER_WHERE_COMPANY.getMessage(), parameterMap);
			namedParameterJdbcTemplate.update(EnumSQLCommand.DELETE_STATEMENT_COMPANY.getMessage(), parameterMap);
			
		} catch (InvalidResultSetAccessException invalidResultSetAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_WRONG_SQL_SYNTAX.getMessage() + invalidResultSetAccessException.getMessage());
		} catch (DataAccessException DataAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + DataAccessException.getMessage());
		}
			throw new DatabaseDAOException("Delete Company");
	}


	public List<Company> findByID(int idSearch) {
		
		Optional<Company> optionalCompany = Optional.empty();
		try {
			MapSqlParameterSource parameterMap = new MapSqlParameterSource().addValue("idCompany", idSearch); 
			
			return namedParameterJdbcTemplate.query(EnumSQLCommand.GET_STATEMENT_COMPANY.getMessage(), parameterMap, companyMapper);
		
		} catch (InvalidResultSetAccessException invalidResultSetAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_WRONG_SQL_SYNTAX.getMessage() + invalidResultSetAccessException.getMessage());
		} catch (DataAccessException DataAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + DataAccessException.getMessage());
		}
			throw new DatabaseDAOException("FindById in Company");
	}

	public List<Company> findAll() {
		
		try  {
			MapSqlParameterSource parameterMap = new MapSqlParameterSource();
			
			return namedParameterJdbcTemplate.query(EnumSQLCommand.GET_ALL_STATEMENT_COMPANY.getMessage(), parameterMap, companyMapper);

		} catch (InvalidResultSetAccessException invalidResultSetAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_WRONG_SQL_SYNTAX.getMessage() + invalidResultSetAccessException.getMessage());
		} catch (DataAccessException DataAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + DataAccessException.getMessage());
		}
			throw new DatabaseDAOException("findAll in Company");
	}

	public int getNbRow() {

		try {
			
			MapSqlParameterSource parameterMap = new MapSqlParameterSource();
			
			return namedParameterJdbcTemplate.queryForObject(EnumSQLCommand.GET_NB_ROW_STATEMENT_COMPANY.getMessage(),
					parameterMap, Integer.class);
			
			
		} catch (InvalidResultSetAccessException invalidResultSetAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_WRONG_SQL_SYNTAX.getMessage() + invalidResultSetAccessException.getMessage());
		} catch (DataAccessException DataAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + DataAccessException.getMessage());
		}
		throw new DatabaseDAOException("NbRows in Company");
	}	

}
