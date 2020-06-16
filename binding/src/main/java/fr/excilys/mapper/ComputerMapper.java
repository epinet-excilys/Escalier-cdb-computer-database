package fr.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import fr.excilys.dao.CompanyDAO;
import fr.excilys.dto.CompanyDTO;
import fr.excilys.dto.ComputerDTO;
import fr.excilys.exception.EnumErrorSQL;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;

@Component
public final class ComputerMapper implements RowMapper<Computer> {

	public static Logger LOGGER = LoggerFactory.getLogger(ComputerMapper.class);
	private CompanyDAO companyDAO;
	
	public ComputerMapper(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
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
		company = companyDAO.findByID(idComp).get(0);

		Computer computer = new Computer.Builder().setIdBuild(id).setNameBuild(name).setIntroducedDateBuild(introDate)
				.setDiscontinuedDateBuild(discoDate).setIdCompagnyBuild(company).build();

		return computer;
	}

	public LocalDate fromStringToLocalDate(String dateInString) throws DateTimeException{

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

		CompanyDTO companyDTO = new CompanyDTO.Builder().build();
		Company company = computer.getCompany();
		
		if(company != null) {
		companyDTO = new CompanyDTO.Builder()
				.setIdBuild((company.getId() == 0 ? 0 : company.getId()))
				.setNameBuild((company.getName() == null ? null : company.getName()))
				.build();
		}
		
		
		ComputerDTO computerDTO = new ComputerDTO.Builder()
				.setNameBuild(computer.getName())
				.setIntroducedDateBuild(computer.getIntroducedDate() == null ? null : computer.getIntroducedDate().toString())
				.setDiscontinuedDateBuild(computer.getDiscontinuedDate() == null ? null : computer.getDiscontinuedDate().toString())
				.setIdBuild(computer.getId())
				.setCompagnyBuild(companyDTO)
				.build();
		
		return computerDTO;
	}
	
	public Computer fromComputerDTOToComputer(ComputerDTO computerDTO) {

		int computerId = computerDTO.getId();
		String computerName = computerDTO.getName();
		LocalDate introducedDate = fromStringToLocalDate(computerDTO.getIntroducedDate());
		LocalDate discontinuedDate = fromStringToLocalDate(computerDTO.getDiscontinuedDate());
		int companyId = computerDTO.getCompanyDTO().getId();

		
		Company company = (companyId != 0 ? (companyDAO.findByID((companyId))).get(0) : (null));

		return new Computer.Builder().setIdBuild(computerId).setNameBuild(computerName).setIntroducedDateBuild(introducedDate)
				.setDiscontinuedDateBuild(discontinuedDate).setIdCompagnyBuild(company).build();
		
	}

	private LocalDate getLocalDateFromResultSet(ResultSet resultSet, String whichOneToGet) throws SQLException {

		return (resultSet.getTimestamp(whichOneToGet) != null
				? resultSet.getTimestamp(whichOneToGet).toLocalDateTime().toLocalDate()
				: null);
	}
	
	@Override
	public Computer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		
		int id = resultSet.getInt("id");
		String name = resultSet.getString("name");
		LocalDate introDate = getLocalDateFromResultSet(resultSet, "introduced");
		LocalDate discoDate = getLocalDateFromResultSet(resultSet, "discontinued");

		int idComp = (resultSet.getInt("company_id"));
		String nameComp = (resultSet.getString("company.name"));

		Company company = new Company.Builder().setIdBuild(idComp).setNameBuild(nameComp).build();
		Computer computer = new Computer.Builder().setIdBuild(id).setNameBuild(name).setIntroducedDateBuild(introDate)
				.setDiscontinuedDateBuild(discoDate).setIdCompagnyBuild(company).build();
		return computer;
	}
}
