package fr.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import fr.excilys.dto.CompanyDTO;
import fr.excilys.model.Company;

public final class CompanyMapper {

	private static volatile CompanyMapper instance = null;

	private CompanyMapper() {
	}

	public final static CompanyMapper getInstance() {

		if (CompanyMapper.instance == null) {

			synchronized (CompanyMapper.class) {
				if (CompanyMapper.instance == null) {
					CompanyMapper.instance = new CompanyMapper();
				}
			}
		}
		return CompanyMapper.instance;

	}
	
	public Company getCompanyFromResultSet(ResultSet resultSet) throws SQLException {
		
		int idComp = resultSet.getInt("id");
		String nameComp = resultSet.getString("name");
	
		return  new Company.Builder().setIdBuild(idComp).setNameBuild(nameComp).build();
	}

	public static CompanyDTO fromCompanyToCompanyDTO(Company company) {
		
		return new CompanyDTO.Builder().setIdBuild(company.getId()).setNameBuild(company.getName()).build();
	}
}
