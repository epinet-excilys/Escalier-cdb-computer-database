package fr.excilys.dao;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.excilys.exception.DatabaseDAOException;
import fr.excilys.exception.EnumErrorSQL;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.model.Computer;

@Repository
public class ComputerDAO {

	public static Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

	private ComputerMapper computerMapper;
	private SessionFactory sessionFactory;

	public ComputerDAO(SessionFactory sessionfactory,ComputerMapper computerMapper) {
		this.sessionFactory = sessionfactory ;
		this.computerMapper = computerMapper;
	}

	@Transactional
	public int create(Computer computer) {

		if (computer != null) {
			if (!computer.getName().isEmpty()) {
				try {
					
					Session session = this.sessionFactory.getCurrentSession();
					session.save(computer);
					
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

	@Transactional
	public int delete(int idSuppression) {

		if ((Integer) idSuppression != null) {
			try {
				
				Session session = this.sessionFactory.getCurrentSession();
				String hqlCommand = EnumHQLCommand.DELETE_STATEMENT.getMessage();
				Query query = session.createQuery(hqlCommand).setParameter("id", idSuppression);
				
				return query.executeUpdate();
				
			} catch (InvalidResultSetAccessException invalidResultSetAccessException) {
				LOGGER.error(
						EnumErrorSQL.BDD_WRONG_SQL_SYNTAX.getMessage() + invalidResultSetAccessException.getMessage());
			} catch (DataAccessException DataAccessException) {
				LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + DataAccessException.getMessage());
			}
		}
		throw new DatabaseDAOException("Delete");
	}

	@Transactional
	public int update(Computer computer) {

		if (!("").equals(computer.getName())) {
			try {
				Session session = this.sessionFactory.getCurrentSession();
				String hqlCommand = EnumHQLCommand.UPDATE_STATEMENT.getMessage();
				Query query = session.createQuery(hqlCommand);

				query.setParameter("id", computer.getId())
						.setParameter("name", computer.getName())
						.setParameter("introduced", getValueOfIntroducedDate(computer))
						.setParameter("discontinued", getValueOfDiscontinuedDate(computer))
						.setParameter("company.id", getValueOfCompany(computer));

				return query.executeUpdate();

			} catch (InvalidResultSetAccessException invalidResultSetAccessException) {
				LOGGER.error(
						EnumErrorSQL.BDD_WRONG_SQL_SYNTAX.getMessage() + invalidResultSetAccessException.getMessage());
			} catch (DataAccessException DataAccessException) {
				LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + DataAccessException.getMessage());
			}
		}
		throw new DatabaseDAOException("Update");
	}

	@Transactional
	public int deleteByGroup(List<Integer> listIDToDelete) {

		try {
			Session session = this.sessionFactory.getCurrentSession();
			String hqlCommand = EnumHQLCommand.DELETE_STATEMENT_GROUP.getMessage();
			Query query = session.createQuery(hqlCommand).setParameter("idList", listIDToDelete);
			
			return query.executeUpdate();
			
		} catch (InvalidResultSetAccessException invalidResultSetAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_WRONG_SQL_SYNTAX.getMessage() + invalidResultSetAccessException.getMessage());
		} catch (DataAccessException DataAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + DataAccessException.getMessage());
		}
		throw new DatabaseDAOException("DeleteByGroup");
	}

	@Transactional
	public List<Computer> findByID(int idSearch) {

		try {
			Session session = this.sessionFactory.getCurrentSession();
			String hqlCommand = EnumHQLCommand.GET_STATEMENT.getMessage();
			@SuppressWarnings("unchecked")
			TypedQuery<Computer> query = (TypedQuery<Computer>) session.createQuery(hqlCommand).setParameter("id",idSearch);
			return query.getResultList();

		} catch (InvalidResultSetAccessException invalidResultSetAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_WRONG_SQL_SYNTAX.getMessage() + invalidResultSetAccessException.getMessage());
		} catch (DataAccessException DataAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + DataAccessException.getMessage());
		}
		throw new DatabaseDAOException("FindById");
	}

	@Transactional
	public List<Computer> findAll() {

		try {

			Session session = this.sessionFactory.getCurrentSession();
			String hqlCommand = EnumHQLCommand.GET_ALL_STATEMENT.getMessage();
			@SuppressWarnings("unchecked")
			TypedQuery<Computer> query = (TypedQuery<Computer>) session.createQuery(hqlCommand);
			
			return query.getResultList();

		} catch (InvalidResultSetAccessException invalidResultSetAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_WRONG_SQL_SYNTAX.getMessage() + invalidResultSetAccessException.getMessage());
		} catch (DataAccessException DataAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + DataAccessException.getMessage());
		}
		throw new DatabaseDAOException("findAll");
	}

	@Transactional
	public List<Computer> findAllPaginate(int ligneDebutOffSet, int taillePage) {

		try {
			Session session = this.sessionFactory.getCurrentSession();
			String hqlCommand = EnumHQLCommand.GET_ALL_STATEMENT.getMessage();
			@SuppressWarnings("unchecked")
			TypedQuery<Computer> query = (TypedQuery<Computer>) session.createQuery(hqlCommand);
			query.setFirstResult(ligneDebutOffSet);
			query.setMaxResults(taillePage);
			
			return query.getResultList();

		} catch (InvalidResultSetAccessException invalidResultSetAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_WRONG_SQL_SYNTAX.getMessage() + invalidResultSetAccessException.getMessage());
		} catch (DataAccessException DataAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + DataAccessException.getMessage());
		}
		throw new DatabaseDAOException("FindAllPaginate");
	}

	@Transactional
	public List<Computer> findAllPaginateSearchLike(String search, int ligneDebutOffSet, int taillePage) {

		try {
			
			Session session = this.sessionFactory.getCurrentSession();
			String hqlCommand = EnumHQLCommand.GET_ALL_PAGINATE_ORDER_LIKE_NAME_STATEMENT.getMessage();
			@SuppressWarnings("unchecked")
			TypedQuery<Computer> query = (TypedQuery<Computer>) session.createQuery(hqlCommand);
			query.setFirstResult(ligneDebutOffSet);
			query.setMaxResults(taillePage);
			query.setParameter("search", search);
			
			return query.getResultList();

		} catch (InvalidResultSetAccessException invalidResultSetAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_WRONG_SQL_SYNTAX.getMessage() + invalidResultSetAccessException.getMessage());
		} catch (DataAccessException DataAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + DataAccessException.getMessage());
		}
		throw new DatabaseDAOException("FindAllPaginateSearch");
	}

	@Transactional
	public List<Computer> findAllPaginateOrder(int ligneDebutOffSet, int taillePage, String order) {

		try {
			Session session = this.sessionFactory.getCurrentSession();
			String hqlCommand = getOrderByStatement(order);
			@SuppressWarnings("unchecked")
			TypedQuery<Computer> query = (TypedQuery<Computer>) session.createQuery(hqlCommand);
			query.setFirstResult(ligneDebutOffSet);
			query.setMaxResults(taillePage);
			
			return query.getResultList();

		} catch (InvalidResultSetAccessException invalidResultSetAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_WRONG_SQL_SYNTAX.getMessage() + invalidResultSetAccessException.getMessage());
		} catch (DataAccessException DataAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + DataAccessException.getMessage());
		}
		throw new DatabaseDAOException("FindAllPaginateOrder");
	}

	@Transactional
	public int getNbRow() {

		try {
			Session session = this.sessionFactory.getCurrentSession();
			String hqlCommand = EnumHQLCommand.GET_NB_ROW_STATEMENT.getMessage();
			Query query = session.createQuery(hqlCommand);
			
			return query.executeUpdate();

		} catch (InvalidResultSetAccessException invalidResultSetAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_WRONG_SQL_SYNTAX.getMessage() + invalidResultSetAccessException.getMessage());
		} catch (DataAccessException DataAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + DataAccessException.getMessage());
		}
		throw new DatabaseDAOException("NbRows");

	}
	@Transactional
	public int getNbRowSearch(String search) {

		try {
			Session session = this.sessionFactory.getCurrentSession();
			String hqlCommand = EnumHQLCommand.GET_NB_ROW_LIKE_STATEMENT.getMessage();
			Query query = session.createQuery(hqlCommand);
			
			return query.executeUpdate();
			
		} catch (InvalidResultSetAccessException invalidResultSetAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_WRONG_SQL_SYNTAX.getMessage() + invalidResultSetAccessException.getMessage());
		} catch (DataAccessException DataAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + DataAccessException.getMessage());
		}
		throw new DatabaseDAOException("NbRowsSearch");
	}

	private String getOrderByStatement(String order) {

		return EnumSQLCommand.GET_ALL_PAGINATE_ORDER_BY_STATEMENT.getMessage() + order + ";";
	}

	private Integer getValueOfCompany(Computer computer) {

		if (computer.getCompany() != null) {
			return computer.getCompany().getId();
		} else {
			return null;
		}
	}

	private Timestamp getValueOfIntroducedDate(Computer computer) {

		return computer.getIntroducedDate() != null
				? Timestamp.valueOf(computer.getIntroducedDate().atTime(LocalTime.MIDNIGHT))
				: null;
	}

	private Timestamp getValueOfDiscontinuedDate(Computer computer) {

		return computer.getDiscontinuedDate() != null
				? Timestamp.valueOf(computer.getDiscontinuedDate().atTime(LocalTime.MIDNIGHT))
				: null;
	}

}
