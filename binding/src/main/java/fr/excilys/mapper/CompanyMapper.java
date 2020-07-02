package fr.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import fr.excilys.dto.CompanyDTO;
import fr.excilys.dto.ComputerDTO;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;

@Component
public final class CompanyMapper implements RowMapper<Company> {

	public Optional<Company> getCompanyFromResultSet(ResultSet resultSet) throws SQLException {
		
		int idComp = resultSet.getInt("id");
		String nameComp = resultSet.getString("name");
		Company company =  new Company.Builder().setIdBuild(idComp).setNameBuild(nameComp).build();
	
		return  Optional.ofNullable(company);
	}

	public static CompanyDTO fromCompanyToCompanyDTO(Company company) {
		
		return new CompanyDTO.Builder().setIdBuild(company.getId()).setNameBuild(company.getName()).build();
	}
	
	public static Company fromCompanyDTOToCompany(CompanyDTO companyDTO) {
		
		return new Company.Builder().setIdBuild(companyDTO.getId()).setNameBuild(companyDTO.getName()).build();
	}
	
	@Override
	public Company mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		
		int idComp = resultSet.getInt("id");
		String nameComp = resultSet.getString("name");
		Company company =  new Company.Builder().setIdBuild(idComp).setNameBuild(nameComp).build();
	
		return  company;
	}
	

}
