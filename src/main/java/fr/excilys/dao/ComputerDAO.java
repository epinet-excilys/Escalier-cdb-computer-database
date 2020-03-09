package fr.excilys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.excilys.exception.DatabaseDAOException;
import fr.excilys.exception.EnumErrorSQL;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.model.Computer;

public final class ComputerDAO {

	private static volatile ComputerDAO instance = null;

	public static Logger LOGGER = LoggerFactory.getLogger(ConnexionSQL.class);

	private ComputerDAO() {
	}

	public final static ComputerDAO getInstance() {
		if (ComputerDAO.instance == null) {
			if (ComputerDAO.instance == null) {
				ComputerDAO.instance = new ComputerDAO();
			}
		}

		return ComputerDAO.instance;
	}

	public int create(Computer computer) {
		if (computer != null) {
			if (!computer.getName().isEmpty()) {
				try (Connection connect = ConnexionSQL.getInstance().getConn();
						PreparedStatement Statement = connect
								.prepareStatement(EnumSQLCommand.CREATE_STATEMENT.getMessage());) {
					setPreparedStatementCreate(Statement, computer);

					return Statement.executeUpdate();

				} catch (SQLException sqlException) {
					LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + sqlException.getMessage());
				} catch (NullPointerException npException) {
					LOGGER.error(EnumErrorSQL.BDD_NULL_OBJECT_LOG.getMessage() + npException.getMessage());
				}
			}
			throw new DatabaseDAOException("Create");
		}
		throw new DatabaseDAOException("ComputerNull");
	}

	public int delete(int idSuppression) {

		try (Connection connect = ConnexionSQL.getInstance().getConn();
				PreparedStatement stmt = connect.prepareStatement(EnumSQLCommand.DELETE_STATEMENT.getMessage());) {
			stmt.setInt(1, idSuppression);

			return stmt.executeUpdate();

		} catch (SQLException sqlException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + sqlException.getMessage());
		}
		throw new DatabaseDAOException("Create");
	}

	public int update(Computer computer) {

		if (!("").equals(computer.getName())) {
			try (Connection connect = ConnexionSQL.getInstance().getConn();
					PreparedStatement statement = connect
							.prepareStatement(EnumSQLCommand.UPDATE_STATEMENT.getMessage());) {
				setPreparedStatementUpdate(statement, computer);

				if (statement.executeUpdate() == 1) {
					return 1;
				} else {
					throw new DatabaseDAOException("Update");
				}

			} catch (SQLException sqlException) {
				LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + sqlException.getMessage());
				throw new DatabaseDAOException("Update");
			} catch (NullPointerException npException) {
				LOGGER.error(EnumErrorSQL.BDD_NULL_OBJECT_LOG.getMessage() + npException.getMessage());
				throw new DatabaseDAOException("Update");
			}
		}
		throw new DatabaseDAOException("Update");
	}

	public Optional<Computer> findByID(int idSearch) {

		Optional<Computer> OptionalComputer = Optional.empty();
		try (Connection connect = ConnexionSQL.getInstance().getConn();
				PreparedStatement stmt = connect.prepareStatement(EnumSQLCommand.GET_STATEMENT.getMessage());
				ResultSet result = setResultSetForFindByID(idSearch, stmt);) {
			if (result.isBeforeFirst()) {
				result.next();
				OptionalComputer = ComputerMapper.getInstance().getComputerFromResultSet(result);
			}

		} catch (SQLException sqlException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + sqlException.getMessage());
			throw new DatabaseDAOException("FindById");
		}

		return OptionalComputer;
	}

	public List<Computer> findAll() {

		List<Computer> computerList = new ArrayList<>();
		Computer computer = new Computer.Builder().build();
		try (Connection connect = ConnexionSQL.getInstance().getConn();
				PreparedStatement stmt = connect.prepareStatement(EnumSQLCommand.GET_ALL_STATEMENT.getMessage());
				ResultSet result = stmt.executeQuery();) {
			if (result.isBeforeFirst()) {
				while (result.next()) {
					computer = ComputerMapper.getInstance().getComputerFromResultSet(result).get();
					computerList.add(computer);
				}
			}

			return computerList;
		} catch (SQLException sqlException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + sqlException.getMessage());
			throw new DatabaseDAOException("findAll");
		}
	}

	public List<Computer> findAllPaginate(int ligneDebutOffSet, int taillePage) {

		List<Computer> computerList = new ArrayList<>();
		Computer computer = new Computer.Builder().build();
		try (Connection connect = ConnexionSQL.getInstance().getConn();
				PreparedStatement stmt = connect
						.prepareStatement(EnumSQLCommand.GET_ALL_PAGINATE_STATEMENT.getMessage());
				ResultSet result = setResultSetWithPreparedStatement(ligneDebutOffSet, taillePage, stmt);) {
			if (result.isBeforeFirst()) {
				while (result.next()) {
					computer = ComputerMapper.getInstance().getComputerFromResultSet(result).get();
					computerList.add(computer);
				}
			}
			return computerList;

		} catch (SQLException sqlException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + sqlException.getMessage());
			throw new DatabaseDAOException("FindAllPaginate");
		}

	}

	public List<Computer> findAllPaginateSearchLike(String search, int ligneDebutOffSet, int taillePage) {

		List<Computer> computerList = new ArrayList<>();
		Computer computer = new Computer.Builder().build();
		try (Connection connect = ConnexionSQL.getInstance().getConn();
				PreparedStatement stmt = connect
						.prepareStatement(EnumSQLCommand.GET_ALL_PAGINATE_ORDER_LIKE_NAME_STATEMENT.getMessage());
				ResultSet result = setResultSetWithPreparedStatementSearch(search, ligneDebutOffSet, taillePage,
						stmt);) {
			if (result.isBeforeFirst()) {
				while (result.next()) {
					computer = ComputerMapper.getInstance().getComputerFromResultSet(result).get();
					computerList.add(computer);
				}
			}

			return computerList;
		} catch (SQLException sqlException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + sqlException.getMessage());
			throw new DatabaseDAOException("FindAllPaginateSearch");
		}
	}

	public List<Computer> findAllPaginateAlphabeticOrder(int ligneDebutOffSet, int taillePage) {

		List<Computer> computerList = new ArrayList<>();
		Computer computer = new Computer.Builder().build();
		try (Connection connect = ConnexionSQL.getInstance().getConn();
				PreparedStatement stmt = connect
						.prepareStatement(EnumSQLCommand.GET_ALL_PAGINATE_ORDER_BY_NAME_STATEMENT.getMessage());
				ResultSet result = setResultSetWithPreparedStatement(ligneDebutOffSet, taillePage, stmt);) {
			if (result.isBeforeFirst()) {
				while (result.next()) {
					computer = ComputerMapper.getInstance().getComputerFromResultSet(result).get();
					computerList.add(computer);
				}
			}

			return computerList;
		} catch (SQLException sqlException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + sqlException.getMessage());
			throw new DatabaseDAOException("FindAllPaginateAplhaOrder");
		}
	}

	public int getNbRow() {

		try (Connection connect = ConnexionSQL.getInstance().getConn();
				PreparedStatement stmt = connect.prepareStatement(EnumSQLCommand.GET_NB_ROW_STATEMENT.getMessage());
				ResultSet result = stmt.executeQuery();) {

			if (result.isBeforeFirst()) {
				result.next();

				return result.getInt("Rows");
			}
		} catch (SQLException sqlException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + sqlException.getMessage());
		}
		throw new DatabaseDAOException("NbRows");

	}

	public int getNbRowSearch(String search) {

		try (Connection connect = ConnexionSQL.getInstance().getConn();
				PreparedStatement stmt = connect
						.prepareStatement(EnumSQLCommand.GET_NB_ROW_LIKE_STATEMENT.getMessage());
				ResultSet result = stmt.executeQuery();) {
			stmt.setString(1, "%" + search + "%");
			if (result.isBeforeFirst()) {
				result.next();

				return result.getInt("Rows");
			}
		} catch (SQLException sqlException) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + sqlException.getMessage());
		}
		throw new DatabaseDAOException("NbRowsSearch");
	}

	private ResultSet setResultSetForFindByID(int idSearch, PreparedStatement stmt) throws SQLException {

		stmt.setInt(1, idSearch);
		ResultSet result = stmt.executeQuery();

		return result;
	}

	private ResultSet setResultSetWithPreparedStatementSearch(String search, int ligneDebutOffSet, int taillePage,
			PreparedStatement stmt) throws SQLException {

		stmt.setString(1, "%" + search + "%");
		stmt.setInt(2, ligneDebutOffSet);
		stmt.setInt(3, taillePage);
		ResultSet result = stmt.executeQuery();

		return result;
	}

	private ResultSet setResultSetWithPreparedStatement(int ligneDebutOffSet, int taillePage, PreparedStatement stmt)
			throws SQLException {

		stmt.setInt(1, ligneDebutOffSet);
		stmt.setInt(2, taillePage);
		ResultSet result = stmt.executeQuery();

		return result;
	}

	private void setPreparedStatementCreate(PreparedStatement preparedStatement, Computer computer)
			throws SQLException {
		preparedStatement.setString(1, computer.getName());
		setBothLocalDateInPreparedStatement(preparedStatement, computer);
		setCompanyInPreparedStatement(preparedStatement, computer);
	}

	private void setPreparedStatementUpdate(PreparedStatement preparedStatement, Computer computer)
			throws SQLException {
		preparedStatement.setInt(5, computer.getId());
		preparedStatement.setString(1, computer.getName());
		setBothLocalDateInPreparedStatement(preparedStatement, computer);
		setCompanyInPreparedStatement(preparedStatement, computer);
	}

	private void setCompanyInPreparedStatement(PreparedStatement preparedStatement, Computer computer)
			throws SQLException {
		if (computer.getCompany() != null) {
			preparedStatement.setInt(4, computer.getCompany().getId());
		} else {
			preparedStatement.setNull(4, java.sql.Types.BIGINT);
		}
	}

	private void setBothLocalDateInPreparedStatement(PreparedStatement preparedStatement, Computer computer)
			throws SQLException {
		preparedStatement.setTimestamp(2,
				computer.getIntroducedDate() != null
						? Timestamp.valueOf(computer.getIntroducedDate().atTime(LocalTime.MIDNIGHT))
						: null);
		preparedStatement.setTimestamp(3,
				computer.getDiscontinuedDate() != null
						? Timestamp.valueOf(computer.getDiscontinuedDate().atTime(LocalTime.MIDNIGHT))
						: null);
	}
}
