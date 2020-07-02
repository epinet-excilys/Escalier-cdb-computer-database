package fr.excilys.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.stereotype.Repository;

import fr.excilys.exception.DatabaseDAOException;
import fr.excilys.exception.EnumErrorSQL;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;

@Repository
public class CompanyDAO {
	
	private final int valueOKTransaction = 1;
	private final int valueFailTransaction = 0;

	public static Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);
	private SessionFactory sessionFactory;

	public CompanyDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public int create(Company company) {

		if (company != null) {
			if (!company.getName().isEmpty()) {
				try {
					
					Session session;
					try {
					    session = sessionFactory.getCurrentSession();
					} catch (HibernateException e) {
					    session = sessionFactory.openSession();
					}
					
					
					session.save(company);
					//TODO Stop-gap mesure, must be reimplemented
					return valueOKTransaction;
					
					
				} catch (InvalidResultSetAccessException invalidResultSetAccessException) {
					LOGGER.error(EnumErrorSQL.BDD_WRONG_SQL_SYNTAX.getMessage()
							+ invalidResultSetAccessException.getMessage());
				} catch (DataAccessException DataAccessException) {
					LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + DataAccessException.getMessage());
				} catch (NullPointerException npException) {
					LOGGER.error(EnumErrorSQL.BDD_NULL_OBJECT_LOG.getMessage() + npException.getMessage());
				}
			}
			throw new DatabaseDAOException("Create");
		}
		throw new DatabaseDAOException("ComputerNull");
	}

 
	public void deleteCompany(int iDCompany) {
		
		try {
			Session session;
			try {
			    session = sessionFactory.getCurrentSession();
			} catch (HibernateException e) {
			    session = sessionFactory.openSession();
			}
			String hqlCommandComputer = EnumHQLCommand.DELETE_STATEMENT_COMPUTER_WHERE_COMPANY.getMessage();
			String hqlCommandCompany = EnumHQLCommand.DELETE_STATEMENT_COMPANY.getMessage();
			
			Query queryComputer = session.createQuery(hqlCommandComputer).setParameter("id", iDCompany);
			Query queryCompany = session.createQuery(hqlCommandCompany).setParameter("id", iDCompany);
			
			queryComputer.executeUpdate();
			queryCompany.executeUpdate();
			
		} catch (InvalidResultSetAccessException invalidResultSetAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_WRONG_SQL_SYNTAX.getMessage() + invalidResultSetAccessException.getMessage());
			throw new DatabaseDAOException("Delete Company");
		} catch (DataAccessException DataAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + DataAccessException.getMessage());
			throw new DatabaseDAOException("Delete Company");
		}
			
	}

	public List<Company> findByID(int idSearch) {
		
		try {
			Session session;
			try {
			    session = sessionFactory.getCurrentSession();
			} catch (HibernateException e) {
			    session = sessionFactory.openSession();
			}
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
			
			Session session;
			try {
			    session = sessionFactory.getCurrentSession();
			} catch (HibernateException e) {
			    session = sessionFactory.openSession();
			}
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

	public List<Company> findAllPaginate(int ligneStartOffSet, int size) {
		try {
			Session session ;
			try {
			    session = sessionFactory.getCurrentSession();
			} catch (HibernateException e) {
			    session = sessionFactory.openSession();
			}
			String hqlCommand = EnumHQLCommand.GET_ALL_STATEMENT_COMPANY.getMessage();
			@SuppressWarnings("unchecked")
			TypedQuery<Company> query = (TypedQuery<Company>) session.createQuery(hqlCommand);
			query.setFirstResult(getValidEntry(ligneStartOffSet));
			query.setMaxResults(size);
			
			return query.getResultList();

		} catch (InvalidResultSetAccessException invalidResultSetAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_WRONG_SQL_SYNTAX.getMessage() + invalidResultSetAccessException.getMessage());
		} catch (DataAccessException DataAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + DataAccessException.getMessage());
		}
		throw new DatabaseDAOException("FindAllPaginate");
	}
	
	private int getValidEntry(int offsetPaginate) {
		return (offsetPaginate > 0) ? offsetPaginate : 0;
	}


}
