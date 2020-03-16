package fr.excilys.dao;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.excilys.exception.DatabaseDAOException;
import fr.excilys.exception.EnumErrorSQL;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.model.Computer;

@Repository
public final class ComputerDAO {

	public static Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

	private ComputerMapper computerMapper;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public ComputerDAO(DataSource datasource, ComputerMapper computerMapper) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(datasource);
		this.computerMapper = computerMapper;
	}

	public int create(Computer computer) {
		if (computer != null) {
			if (!computer.getName().isEmpty()) {
				try {

					MapSqlParameterSource parameterMap = new MapSqlParameterSource()
							.addValue("name", computer.getName())
							.addValue("introduced", getValueOfIntroducedDate(computer))
							.addValue("discontinued", getValueOfDiscontinuedDate(computer))
							.addValue("company.id", getValueOfCompany(computer));

					return namedParameterJdbcTemplate.update(EnumSQLCommand.CREATE_STATEMENT.getMessage(),
							parameterMap);

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

	public int delete(int idSuppression) {

		if ((Integer) idSuppression != null) {
			try {
				MapSqlParameterSource parameterMap = new MapSqlParameterSource().addValue("id", idSuppression);

				return namedParameterJdbcTemplate.update(EnumSQLCommand.DELETE_STATEMENT.getMessage(), parameterMap);

			} catch (InvalidResultSetAccessException invalidResultSetAccessException) {
				LOGGER.error(
						EnumErrorSQL.BDD_WRONG_SQL_SYNTAX.getMessage() + invalidResultSetAccessException.getMessage());
			} catch (DataAccessException DataAccessException) {
				LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + DataAccessException.getMessage());
			}
		}
		throw new DatabaseDAOException("Delete");
	}

	public int update(Computer computer) {

		if (!("").equals(computer.getName())) {
			try {

				MapSqlParameterSource parameterMap = new MapSqlParameterSource().addValue("id", computer.getId())
						.addValue("name", computer.getName()).addValue("introduced", getValueOfIntroducedDate(computer))
						.addValue("discontinued", getValueOfDiscontinuedDate(computer))
						.addValue("company.id", getValueOfCompany(computer));

				return namedParameterJdbcTemplate.update(EnumSQLCommand.UPDATE_STATEMENT.getMessage(), parameterMap);

			} catch (InvalidResultSetAccessException invalidResultSetAccessException) {
				LOGGER.error(
						EnumErrorSQL.BDD_WRONG_SQL_SYNTAX.getMessage() + invalidResultSetAccessException.getMessage());
			} catch (DataAccessException DataAccessException) {
				LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + DataAccessException.getMessage());
			}
		}
		throw new DatabaseDAOException("Update");
	}

	public int deleteByGroup(List<Integer> listIDToDelete) {

		try {

			Map<String, List<Integer>> namedParameters = Collections.singletonMap("idList", listIDToDelete);

			return namedParameterJdbcTemplate.update(EnumSQLCommand.DELETE_STATEMENT_GROUP.getMessage(),
					namedParameters);

		} catch (InvalidResultSetAccessException invalidResultSetAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_WRONG_SQL_SYNTAX.getMessage() + invalidResultSetAccessException.getMessage());
		} catch (DataAccessException DataAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + DataAccessException.getMessage());
		}
		throw new DatabaseDAOException("DeleteByGroup");
	}

	public List<Computer> findByID(int idSearch) {

		try {
			MapSqlParameterSource parameterMap = new MapSqlParameterSource().addValue("id", idSearch);

			return namedParameterJdbcTemplate.query(EnumSQLCommand.GET_STATEMENT.getMessage(), parameterMap,
					computerMapper);

		} catch (InvalidResultSetAccessException invalidResultSetAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_WRONG_SQL_SYNTAX.getMessage() + invalidResultSetAccessException.getMessage());
		} catch (DataAccessException DataAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + DataAccessException.getMessage());
		}
		throw new DatabaseDAOException("FindById");
	}

	public List<Computer> findAll() {

		try {

			return namedParameterJdbcTemplate.query(EnumSQLCommand.GET_ALL_STATEMENT.getMessage(), computerMapper);

		} catch (InvalidResultSetAccessException invalidResultSetAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_WRONG_SQL_SYNTAX.getMessage() + invalidResultSetAccessException.getMessage());
		} catch (DataAccessException DataAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + DataAccessException.getMessage());
		}
		throw new DatabaseDAOException("findAll");
	}

	public List<Computer> findAllPaginate(int ligneDebutOffSet, int taillePage) {

		try {
			MapSqlParameterSource parameterMap = new MapSqlParameterSource().addValue("offset", ligneDebutOffSet)
					.addValue("pageSize", taillePage);

			return namedParameterJdbcTemplate.query(EnumSQLCommand.GET_ALL_PAGINATE_STATEMENT.getMessage(),
					parameterMap, computerMapper);

		} catch (InvalidResultSetAccessException invalidResultSetAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_WRONG_SQL_SYNTAX.getMessage() + invalidResultSetAccessException.getMessage());
		} catch (DataAccessException DataAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + DataAccessException.getMessage());
		}
		throw new DatabaseDAOException("FindAllPaginate");
	}

	public List<Computer> findAllPaginateSearchLike(String search, int ligneDebutOffSet, int taillePage) {
		try {
			MapSqlParameterSource parameterMap = new MapSqlParameterSource().addValue("offset", ligneDebutOffSet)
					.addValue("pageSize", taillePage).addValue("search", '%' + search + '%');

			return namedParameterJdbcTemplate.query(
					EnumSQLCommand.GET_ALL_PAGINATE_ORDER_LIKE_NAME_STATEMENT.getMessage(), parameterMap,
					computerMapper);

		} catch (InvalidResultSetAccessException invalidResultSetAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_WRONG_SQL_SYNTAX.getMessage() + invalidResultSetAccessException.getMessage());
		} catch (DataAccessException DataAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + DataAccessException.getMessage());
		}
		throw new DatabaseDAOException("FindAllPaginateSearch");
	}

	public List<Computer> findAllPaginateOrder(int ligneDebutOffSet, int taillePage, String order) {

		try {
			MapSqlParameterSource parameterMap = new MapSqlParameterSource().addValue("offset", ligneDebutOffSet)
					.addValue("pageSize", taillePage);

			return namedParameterJdbcTemplate.query(getOrderByStatement(order), parameterMap, computerMapper);

		} catch (InvalidResultSetAccessException invalidResultSetAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_WRONG_SQL_SYNTAX.getMessage() + invalidResultSetAccessException.getMessage());
		} catch (DataAccessException DataAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + DataAccessException.getMessage());
		}
		throw new DatabaseDAOException("FindAllPaginateOrder");
	}

	public int getNbRow() {

		try {
			MapSqlParameterSource parameterMap = new MapSqlParameterSource();
			return namedParameterJdbcTemplate.queryForObject(EnumSQLCommand.GET_NB_ROW_STATEMENT.getMessage(),
					parameterMap, Integer.class);

		} catch (InvalidResultSetAccessException invalidResultSetAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_WRONG_SQL_SYNTAX.getMessage() + invalidResultSetAccessException.getMessage());
		} catch (DataAccessException DataAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + DataAccessException.getMessage());
		}
		throw new DatabaseDAOException("NbRows");

	}

	public int getNbRowSearch(String search) {

		try {
			MapSqlParameterSource parameterMap = new MapSqlParameterSource().addValue("search", '%' + search + '%');
			return namedParameterJdbcTemplate.queryForObject(EnumSQLCommand.GET_NB_ROW_LIKE_STATEMENT.getMessage(),
					parameterMap, Integer.class);

		} catch (InvalidResultSetAccessException invalidResultSetAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_WRONG_SQL_SYNTAX.getMessage() + invalidResultSetAccessException.getMessage());
		} catch (DataAccessException DataAccessException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + DataAccessException.getMessage());
		}
		throw new DatabaseDAOException("NbRowsSearch");
	}

	private String getOrderByStatement(String order) {
		return EnumSQLCommand.GET_ALL_PAGINATE_ORDER_BY_STATEMENT.getMessage() + order + " LIMIT :offset, :pageSize;";
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
