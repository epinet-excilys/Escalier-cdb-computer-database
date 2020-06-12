package fr.excilys.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.TypedQuery;
import javax.sql.DataSource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
import fr.excilys.model.Computer;

@Repository
public class CompanyDAO {

	public static Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);
	private SessionFactory sessionFactory;

	public CompanyDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

 
	public void deleteCompany(int iDCompany) {
		
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String hqlCommandComputer = EnumHQLCommand.DELETE_STATEMENT_COMPUTER_WHERE_COMPANY.getMessage();
			String hqlCommandCompany = EnumHQLCommand.DELETE_STATEMENT_COMPANY.getMessage();
			
			Query queryComputer = session.createQuery(hqlCommandComputer).setParameter("id", iDCompany);
			Query queryCompany = session.createQuery(hqlCommandCompany).setParameter("id", iDCompany);
			
			queryComputer.executeUpdate();
			queryCompany.executeUpdate();
			
		} catch (InvalidResultSetAccessException invalidResultSetAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_WRONG_SQL_SYNTAX.getMessage() + invalidResultSetAccessException.getMessage());
		} catch (DataAccessException DataAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + DataAccessException.getMessage());
		}
			throw new DatabaseDAOException("Delete Company");
	}

	public List<Company> findByID(int idSearch) {
		
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String hqlCommand = EnumHQLCommand.GET_STATEMENT_COMPANY.getMessage();
			@SuppressWarnings("unchecked")
			TypedQuery<Company> query = (TypedQuery<Company>) session.createQuery(hqlCommand).setParameter("idCompany",idSearch);
			return query.getResultList();
		
		} catch (InvalidResultSetAccessException invalidResultSetAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_WRONG_SQL_SYNTAX.getMessage() + invalidResultSetAccessException.getMessage());
		} catch (DataAccessException DataAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + DataAccessException.getMessage());
		}
			throw new DatabaseDAOException("FindById in Company");
	}

	public List<Company> findAll() {
		
		try  {
			
			Session session = this.sessionFactory.getCurrentSession();
			String hqlCommand = EnumHQLCommand.GET_ALL_STATEMENT_COMPANY.getMessage();
			@SuppressWarnings("unchecked")
			TypedQuery<Company> query = (TypedQuery<Company>) session.createQuery(hqlCommand);
			return query.getResultList();

		} catch (InvalidResultSetAccessException invalidResultSetAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_WRONG_SQL_SYNTAX.getMessage() + invalidResultSetAccessException.getMessage());
		} catch (DataAccessException DataAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + DataAccessException.getMessage());
		}
			throw new DatabaseDAOException("findAll in Company");
	}


	public int getNbRow() {

		try {
			
			Session session = this.sessionFactory.getCurrentSession();
			String hqlCommand = EnumHQLCommand.GET_NB_ROW_STATEMENT_COMPANY.getMessage();
			Query query = session.createQuery(hqlCommand);
			
			return query.executeUpdate();
			
		} catch (InvalidResultSetAccessException invalidResultSetAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_WRONG_SQL_SYNTAX.getMessage() + invalidResultSetAccessException.getMessage());
		} catch (DataAccessException DataAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + DataAccessException.getMessage());
		}
		throw new DatabaseDAOException("NbRows in Company");
	}	

}
