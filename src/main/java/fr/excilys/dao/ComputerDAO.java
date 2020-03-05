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
	
	private static final String BDD_ACCESS_LOG = "Impossible de se connecter à la  BDD niveau DAO";
	private static final String BDD_NULL_OBJECT_LOG = "Tentative de manipulation d'un objet null";

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
						PreparedStatement stmt = connect.prepareStatement(CREATE_STATEMENT);) {
					stmt.setString(1, computer.getName());
					stmt.setTimestamp(2,
							computer.getIntroducedDate() != null
									? Timestamp.valueOf(computer.getIntroducedDate().atTime(LocalTime.MIDNIGHT))
									: null);
					stmt.setTimestamp(3,
							computer.getDiscontinuedDate() != null
									? Timestamp.valueOf(computer.getDiscontinuedDate().atTime(LocalTime.MIDNIGHT))
									: null);
					if (computer.getCompany() != null) {
						stmt.setInt(4, computer.getCompany().getId());
					} else {
						stmt.setNull(4, java.sql.Types.BIGINT);
					}

					nbOfRowInsertedInDB = stmt.executeUpdate();

				} catch (SQLException e1) {
					LOGGER.error(BDD_ACCESS_LOG + e1.getMessage());
				} catch (NullPointerException e2) {
					LOGGER.error(BDD_NULL_OBJECT_LOG + e2.getMessage());
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

		} catch (SQLException e) {
			LOGGER.error(BDD_ACCESS_LOG);
		}
		return nbOfDeletedRowsinDB;
	}

	public int update(Computer computer) {
		int nbOfUpdatedRowsinDB = 0;

		if (computer != null) {
			try (Connection connect = ConnexionSQL.getInstance().getConn();
					PreparedStatement stmt = connect.prepareStatement(UPDATE_STATEMENT);) {

				stmt.setInt(5, computer.getId());
				stmt.setString(1, computer.getName());
				stmt.setTimestamp(2,
						computer.getIntroducedDate() != null
								? Timestamp.valueOf(computer.getIntroducedDate().atTime(LocalTime.MIDNIGHT))
								: null);
				stmt.setTimestamp(3,
						computer.getDiscontinuedDate() != null
								? Timestamp.valueOf(computer.getDiscontinuedDate().atTime(LocalTime.MIDNIGHT))
								: null);
				if (computer.getCompany() != null) {
					stmt.setInt(4, computer.getCompany().getId());
				} else {
					stmt.setNull(4, java.sql.Types.BIGINT);
				}
				nbOfUpdatedRowsinDB = stmt.executeUpdate();
			} catch (SQLException e1) {
				LOGGER.error(BDD_ACCESS_LOG + e1.getMessage());
			} catch (NullPointerException e2) {
				LOGGER.error(BDD_NULL_OBJECT_LOG + e2.getMessage());
			}
		}
		return nbOfUpdatedRowsinDB;

	}

	public Optional<Computer> findByID(int idSearch) {
		Optional<Computer> computer = Optional.empty();
		try (Connection connect = ConnexionSQL.getInstance().getConn();
				PreparedStatement stmt = connect.prepareStatement(GET_STATEMENT);) {
			stmt.setInt(1, idSearch);

			try (ResultSet result = stmt.executeQuery()) {

				if (result.first()) {
					computer = ComputerMapper.getInstance().getComputerFromResultSet(result);
				}
			}

		} catch (SQLException e1) {
			LOGGER.error(BDD_ACCESS_LOG + e1.getMessage());
		}
		return computer;
	}

	public List<Computer> findAll() {
		List<Computer> computerList = new ArrayList<>();
		Computer computer = new Computer.Builder().build();
		try (Connection connect = ConnexionSQL.getInstance().getConn();
				PreparedStatement stmt = connect.prepareStatement(GET_ALL_STATEMENT);) {
			try (ResultSet result = stmt.executeQuery();) {
				while (result.next()) {
					computer = ComputerMapper.getInstance().getComputerFromResultSet(result).get();
					computerList.add(computer);
				}
			}
		} catch (SQLException e) {
			LOGGER.error(BDD_ACCESS_LOG);
		}
		return computerList;
	}

	public List<Computer> findAllPaginate(int ligneDebutOffSet, int taillePage) {
		List<Computer> computerList = new ArrayList<>();
		Computer computer = new Computer.Builder().build();
		try (Connection connect = ConnexionSQL.getInstance().getConn();
				PreparedStatement stmt = connect.prepareStatement(GET_ALL_PAGINATE_STATEMENT);) {
			stmt.setInt(1, ligneDebutOffSet);
			stmt.setInt(2, taillePage);
			try (ResultSet result = stmt.executeQuery()) {
				while (result.next()) {
					computer = ComputerMapper.getInstance().getComputerFromResultSet(result).get();
					computerList.add(computer);
				}
			}
		} catch (SQLException e) {
			LOGGER.error(BDD_ACCESS_LOG);
		}
		return computerList;
	}
	
	public List<Computer> findAllPaginateSearchLike(String search, int ligneDebutOffSet, int taillePage) {
		List<Computer> computerList = new ArrayList<>();
		Computer computer = new Computer.Builder().build();
		try (Connection connect = ConnexionSQL.getInstance().getConn();
				PreparedStatement stmt = connect.prepareStatement(GET_ALL_PAGINATE_ORDER_LIKE_NAME_STATEMENT);) {
			stmt.setString(1, "%" + search + "%");
			stmt.setInt(2, ligneDebutOffSet);
			stmt.setInt(3, taillePage);
			try (ResultSet result = stmt.executeQuery()) {
				while (result.next()) {
					computer = ComputerMapper.getInstance().getComputerFromResultSet(result).get();
					computerList.add(computer);
				}
			}
		} catch (SQLException e) {
			LOGGER.error(BDD_ACCESS_LOG);
		}
		return computerList;
	}
	
	public List<Computer> findAllPaginateAlphabeticOrder(int ligneDebutOffSet, int taillePage) {
		List<Computer> computerList = new ArrayList<>();
		Computer computer = new Computer.Builder().build();
		try (Connection connect = ConnexionSQL.getInstance().getConn();
				PreparedStatement stmt = connect.prepareStatement(GET_ALL_PAGINATE_ORDER_BY_NAME_STATEMENT);) {
			stmt.setInt(1, ligneDebutOffSet);
			stmt.setInt(2, taillePage);
			try (ResultSet result = stmt.executeQuery()) {
				while (result.next()) {
					computer = ComputerMapper.getInstance().getComputerFromResultSet(result).get();
					computerList.add(computer);
				}
			}
		} catch (SQLException e) {
			LOGGER.error(BDD_ACCESS_LOG);
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
		} catch (SQLException e) {
			LOGGER.error(BDD_ACCESS_LOG);
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
					System.err.println("TO ERR IS HUMAN ----------------------" + nbRow);
				}
			}
		} catch (SQLException e) {
			LOGGER.error(BDD_ACCESS_LOG);
		}
		return nbRow;
	}

}
