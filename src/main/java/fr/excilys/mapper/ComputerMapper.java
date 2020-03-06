package fr.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import fr.excilys.dao.CompanyDAO;
import fr.excilys.dao.ConnexionSQL;
import fr.excilys.dto.CompanyDTO;
import fr.excilys.dto.ComputerDTO;
import fr.excilys.exception.EnumErrorSQL;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;

public final class ComputerMapper {

	private static volatile ComputerMapper instance = null;
	public static Logger LOGGER = LoggerFactory.getLogger(ConnexionSQL.class);

	private ComputerMapper() {
	}

	public final static ComputerMapper getInstance() {

		if (ComputerMapper.instance == null) {
			if (ComputerMapper.instance == null) {
				ComputerMapper.instance = new ComputerMapper();
			}
		}
		return ComputerMapper.instance;

	}

	public Optional<Computer> getComputerFromResultSet(ResultSet resultSet) {

		Computer computer = new Computer.Builder().build();

		try {
			int id = resultSet.getInt("id");
			String name = resultSet.getString("name");
			LocalDate introDate = getLocalDateFromResultSet(resultSet, "introduced");
			LocalDate discoDate = getLocalDateFromResultSet(resultSet, "discontinued");

			int idComp = (resultSet.getInt("company_id"));
			String nameComp = (resultSet.getString("company.name"));

			Company company = new Company.Builder().setIdBuild(idComp).setNameBuild(nameComp).build();
			computer = new Computer.Builder().setIdBuild(id).setNameBuild(name).setIntroducedDateBuild(introDate)
					.setDiscontinuedDateBuild(discoDate).setIdCompagnyBuild(company).build();
		} catch (SQLException e1) {
			LOGGER.error(EnumErrorSQL.SQL_EXCEPTION_LOG.getMessage() + e1.getMessage());
		}

		return Optional.ofNullable(computer);
	}

	public Computer fromStringToComput(String[] resultTab) {

		int id = Integer.parseInt(resultTab[0]);
		String name = resultTab[1];
		LocalDate introDate = fromStringToLocalDate(resultTab[2]);
		LocalDate discoDate = fromStringToLocalDate(resultTab[3]);

		int idComp = Integer.parseInt(resultTab[4]);
		Company company = new Company.Builder().build();
		company = CompanyDAO.getInstance().findByID(idComp).get();

		Computer computer = new Computer.Builder().setIdBuild(id).setNameBuild(name).setIntroducedDateBuild(introDate)
				.setDiscontinuedDateBuild(discoDate).setIdCompagnyBuild(company).build();

		return computer;
	}

	public LocalDate fromStringToLocalDate(String dateInString) {

		if ((dateInString != null) && !dateInString.isEmpty()) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate dateTime;
			dateTime = LocalDate.parse(dateInString, formatter);
			return dateTime;
		} else {
			return null;
		}
	}

	public ComputerDTO fromComputerToComputerDTO(Computer computer) {

		CompanyDTO companyDTO = new CompanyDTO.Builder()
				.setIdBuild((computer.getCompany().getId() == 0 ? 0 : computer.getCompany().getId()))
				.setNameBuild((computer.getCompany().getName() == null ? null : computer.getCompany().getName()))
				.build();

		ComputerDTO computerDTO = new ComputerDTO(computer.getName(),
				computer.getIntroducedDate() == null ? null : computer.getIntroducedDate().toString(),
				computer.getDiscontinuedDate() == null ? null : computer.getDiscontinuedDate().toString(), companyDTO);
		computerDTO.setId(computer.getId());

		return computerDTO;
	}

	private LocalDate getLocalDateFromResultSet(ResultSet resultSet, String whichOneToGet) throws SQLException {

		return (resultSet.getTimestamp(whichOneToGet) != null
				? resultSet.getTimestamp(whichOneToGet).toLocalDateTime().toLocalDate()
				: null);
	}
}
