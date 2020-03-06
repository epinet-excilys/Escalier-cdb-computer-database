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

import fr.excilys.exception.EnumErrorSQL;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.model.Computer;

public final class ComputerDAO {

	private static volatile ComputerDAO instance = null;
	private static final String CREATE_STATEMENT = "INSERT INTO computer(name, introduced, discontinued, company_id) "
			+ "VALUES(?, ?, ?, ?);";
	private static final String UPDATE_STATEMENT = "UPDATE computer set name=?, introduced=? , discontinued=?, company_id=? where id=?;";
	private static final String DELETE_STATEMENT = "DELETE from computer where id=?;";
	private static final String GET_STATEMENT = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name"
			+ " FROM computer  LEFT JOIN company ON company_id = company.id WHERE computer.id = ?;";
	private static final String GET_ALL_STATEMENT = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name "
			+ "FROM computer LEFT JOIN company ON company_id = company.id ;";
	private static final String GET_ALL_PAGINATE_STATEMENT = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name "
			+ "FROM computer LEFT JOIN company ON company_id = company.id LIMIT ?, ?;";
	private static final String GET_ALL_PAGINATE_ORDER_LIKE_NAME_STATEMENT = "SELECT computer.id, computer.name, computer.introduced , computer.discontinued , company_id, company.name "
			+ "FROM computer LEFT JOIN company ON company_id = company.id WHERE computer.name LIKE ? LIMIT ?,?;";
	private static final String GET_ALL_PAGINATE_ORDER_BY_NAME_STATEMENT = "SELECT computer.id, computer.name, computer.introduced , computer.discontinued , company_id, company.name "
			+ "FROM computer LEFT JOIN company ON company_id = company.id ORDER BY computer.name LIMIT ?,?;";
	private static final String GET_NB_ROW_STATEMENT = "SELECT COUNT(*) as \"Rows\" FROM computer;";
	private static final String GET_NB_ROW_LIKE_STATEMENT = "SELECT COUNT(*) as \"Rows\" FROM computer WHERE computer.name LIKE ?;";

	public static Logger LOGGER = LoggerFactory.getLogger(ConnexionSQL.class);

	private ComputerDAO() {
		super();

	}

	public final static ComputerDAO getInstance() {
		if (ComputerDAO.instance == null) {
			synchronized (ComputerDAO.class) {
				if (ComputerDAO.instance == null) {
					ComputerDAO.instance = new ComputerDAO();
				}
			}
		}

		return ComputerDAO.instance;
	}

	public int create(Computer computer) {
		int nbOfRowInsertedInDB = 0;
		if (computer != null) {
			if (!computer.getName().isEmpty()) {
				try (Connection connect = ConnexionSQL.getInstance().getConn();
						PreparedStatement Statement = connect.prepareStatement(CREATE_STATEMENT);) {
					setPreparedStatementCreate(Statement, computer);
					nbOfRowInsertedInDB = Statement.executeUpdate();
				} catch (SQLException e1) {
					LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + e1.getMessage());
				} catch (NullPointerException e2) {
					LOGGER.error(EnumErrorSQL.BDD_NULL_OBJECT_LOG.getMessage() + e2.getMessage());
				}
			}
		}
		return nbOfRowInsertedInDB;
	}

	public int delete(int idSuppression) {
		int nbOfDeletedRowsinDB = 0;
		try (Connection connect = ConnexionSQL.getInstance().getConn();
				PreparedStatement stmt = connect.prepareStatement(DELETE_STATEMENT);) {
			stmt.setInt(1, idSuppression);
			nbOfDeletedRowsinDB = stmt.executeUpdate();
		} catch (SQLException e1) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + e1.getMessage());
		}
		return nbOfDeletedRowsinDB;
	}

	public int update(Computer computer) {
		int nbOfUpdatedRowsinDB = 0;
		if (computer != null) {
			try (Connection connect = ConnexionSQL.getInstance().getConn();
					PreparedStatement statement = connect.prepareStatement(UPDATE_STATEMENT);) {
				setPreparedStatementUpdate(statement, computer);
				nbOfUpdatedRowsinDB = statement.executeUpdate();
			} catch (SQLException e1) {
				LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + e1.getMessage());
			} catch (NullPointerException e2) {
				LOGGER.error(EnumErrorSQL.BDD_NULL_OBJECT_LOG.getMessage() + e2.getMessage());
			}
		}
		return nbOfUpdatedRowsinDB;
	}

	public Optional<Computer> findByID(int idSearch) {
		Optional<Computer> OptionalComputer = Optional.empty();
		try (Connection connect = ConnexionSQL.getInstance().getConn();
				PreparedStatement stmt = connect.prepareStatement(GET_STATEMENT);
					ResultSet result = setResultSetForFindByID(idSearch, stmt);) {
			if (result.first()) {
				OptionalComputer = ComputerMapper.getInstance().getComputerFromResultSet(result);
			}
		} catch (SQLException e1) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + e1.getMessage());
		}
		return OptionalComputer;
	}

	public List<Computer> findAll() {
		List<Computer> computerList = new ArrayList<>();
		Computer computer = new Computer.Builder().build();
		try (Connection connect = ConnexionSQL.getInstance().getConn();
				PreparedStatement stmt = connect.prepareStatement(GET_ALL_STATEMENT);
					ResultSet result = stmt.executeQuery();) {
			while (result.next()) {
				computer = ComputerMapper.getInstance().getComputerFromResultSet(result).get();
				computerList.add(computer);
			}
		} catch (SQLException e1) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + e1.getMessage());
		}
		return computerList;
	}

	public List<Computer> findAllPaginate(int ligneDebutOffSet, int taillePage) {
		List<Computer> computerList = new ArrayList<>();
		Computer computer = new Computer.Builder().build();
		try (Connection connect = ConnexionSQL.getInstance().getConn();
				PreparedStatement stmt = connect.prepareStatement(GET_ALL_PAGINATE_STATEMENT);
				ResultSet result = setResultSetWithPreparedStatement(ligneDebutOffSet, taillePage, stmt);) {
			while (result.next()) {
				computer = ComputerMapper.getInstance().getComputerFromResultSet(result).get();
				computerList.add(computer);
			}
		} catch (SQLException e1) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + e1.getMessage());
		}
		return computerList;
	}

	public List<Computer> findAllPaginateSearchLike(String search, int ligneDebutOffSet, int taillePage) {
		List<Computer> computerList = new ArrayList<>();
		Computer computer = new Computer.Builder().build();
		try (Connection connect = ConnexionSQL.getInstance().getConn();
				PreparedStatement stmt = connect.prepareStatement(GET_ALL_PAGINATE_ORDER_LIKE_NAME_STATEMENT);
				ResultSet result = setResultSetWithPreparedStatementSearch(search, ligneDebutOffSet, taillePage,
						stmt);) {
			while (result.next()) {
				computer = ComputerMapper.getInstance().getComputerFromResultSet(result).get();
				computerList.add(computer);
			}
		} catch (SQLException e1) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + e1.getMessage());
		}
		return computerList;
	}

	public List<Computer> findAllPaginateAlphabeticOrder(int ligneDebutOffSet, int taillePage) {
		List<Computer> computerList = new ArrayList<>();
		Computer computer = new Computer.Builder().build();
		try (Connection connect = ConnexionSQL.getInstance().getConn();
				PreparedStatement stmt = connect.prepareStatement(GET_ALL_PAGINATE_ORDER_BY_NAME_STATEMENT);
				ResultSet result = setResultSetWithPreparedStatement(ligneDebutOffSet, taillePage, stmt);) {
			while (result.next()) {
				computer = ComputerMapper.getInstance().getComputerFromResultSet(result).get();
				computerList.add(computer);
			}
		} catch (SQLException e1) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + e1.getMessage());
		}
		return computerList;
	}

	public int getNbRow() {
		int nbRow = -1;
		try (Connection connect = ConnexionSQL.getInstance().getConn();
				PreparedStatement stmt = connect.prepareStatement(GET_NB_ROW_STATEMENT);) {
			try (ResultSet result = stmt.executeQuery()) {
				if (result.first()) {
					nbRow = result.getInt("Rows");
				}
			}
		} catch (SQLException e1) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + e1.getMessage());
		}
		return nbRow;
	}

	public int getNbRowSearch(String search) {
		int nbRow = -1;
		try (Connection connect = ConnexionSQL.getInstance().getConn();
				PreparedStatement stmt = connect.prepareStatement(GET_NB_ROW_LIKE_STATEMENT);) {
			stmt.setString(1, "%" + search + "%");
			try (ResultSet result = stmt.executeQuery()) {
				if (result.first()) {
					nbRow = result.getInt("Rows");
				}
			}
		} catch (SQLException e1) {
			LOGGER.error(EnumErrorSQL.BDD_ACCESS_LOG.getMessage() + e1.getMessage());
		}
		return nbRow;
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
