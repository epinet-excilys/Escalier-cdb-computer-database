package fr.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import fr.excilys.dao.CompanyDAO;
import fr.excilys.dto.CompanyDTO;
import fr.excilys.dto.ComputerDTO;
import fr.excilys.exception.Logging;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;

public final class ComputerMapper {

	private static volatile ComputerMapper instance = null;
	private static final String SQL_EXCEPTION_LOG = "La manipulation du ResultSet a provoquer une error";

	private ComputerMapper() {
		super();
	}

	public final static ComputerMapper getInstance() {

		if (ComputerMapper.instance == null) {

			synchronized (ComputerMapper.class) {
				if (ComputerMapper.instance == null) {
					ComputerMapper.instance = new ComputerMapper();
				}
			}
		}
		return ComputerMapper.instance;

	}

	public Optional<Computer> getComputerFromResultSet(ResultSet resultSet) {
		Computer computer = new Computer.Builder().build();
		try {
			int id = resultSet.getInt("id");
			String name = resultSet.getString("name");
			LocalDate introDate = (resultSet.getTimestamp("introduced") != null
					? resultSet.getTimestamp("introduced").toLocalDateTime().toLocalDate()
					: null);
			LocalDate discoDate = (resultSet.getTimestamp("discontinued") != null
					? resultSet.getTimestamp("discontinued").toLocalDateTime().toLocalDate()
					: null);
			int idComp = (resultSet.getInt("company_id"));
			String nameComp = (resultSet.getString("company.name"));
			Company company = new Company.Builder().setIdBuild(idComp).setNameBuild(nameComp).build();
			computer = new Computer.Builder().setIdBuild(id).setNameBuild(name).setIntroducedDateBuild(introDate)
					.setDiscontinuedDateBuild(discoDate).setIdCompagnyBuild(company).build();
		} catch (SQLException e1) {
			Logging.getLog().error(SQL_EXCEPTION_LOG + e1.getMessage());
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

	public LocalDate fromStringToLocalDate(String s) {
		if ((s != null) && !s.isEmpty()) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate dateTime;
			dateTime = LocalDate.parse(s, formatter);

			return dateTime;
		} else {
			return null;
		}
	}

	public ComputerDTO fromComputerToComputerDTO(Computer computer) {
		CompanyDTO companyDTO = new CompanyDTO.Builder().build();
		companyDTO.setId((computer.getCompany().getId() == 0 ? 0 : computer.getCompany().getId()));
		companyDTO.setName((computer.getCompany().getName() == null ? null : computer.getCompany().getName()));

		ComputerDTO computerDTO = new ComputerDTO(computer.getName(),
				computer.getIntroducedDate() == null ? null : computer.getIntroducedDate().toString(),
				computer.getDiscontinuedDate() == null ? null : computer.getDiscontinuedDate().toString(), companyDTO);
		computerDTO.setId(computer.getId());
		return computerDTO;
	}

}
