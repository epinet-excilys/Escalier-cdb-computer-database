package fr.excilys.dto;

import java.time.LocalDate;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.excilys.model.Company;
import fr.excilys.model.Computer;
import fr.excilys.model.Computer.Builder;

@Service
public class ComputerDTO {

	private int id;
	private String name;
	private String introducedDate;
	private String discontinuedDate;
	private CompanyDTO companyDTO;

	public ComputerDTO(String name, String introDateString, String discoDateString, CompanyDTO companyDTO) {
		this.name = name;
		this.introducedDate = introDateString;
		this.discontinuedDate = discoDateString;
		this.companyDTO = companyDTO;
	}

	public ComputerDTO(Builder builder) {
		this.id = builder.idBuild;
		this.name = builder.nameBuild;
		this.introducedDate = builder.introducedDateBuild;
		this.discontinuedDate = builder.discontinuedDateBuild;
		this.companyDTO = builder.companyDTOBuild;
	}
	
	//TODO : VIREZ CA

//	public ComputerDTO(@JsonProperty("name") String name, @JsonProperty("introducedDate") String introducedDate,
//			@JsonProperty("discontinuedDate") String discontinuedDate, @JsonProperty("companyDTO") CompanyDTO companyDTo) {
//	}
	
	public ComputerDTO() {
		
	}

	public static class Builder {
		private int idBuild;
		private String nameBuild;
		private String introducedDateBuild;
		private String discontinuedDateBuild;
		private CompanyDTO companyDTOBuild;

		public Builder setIdBuild(int id) {
			this.idBuild = id;
			return this;
		}

		public Builder setNameBuild(String name) {
			this.nameBuild = name;
			return this;
		}

		public Builder setIntroducedDateBuild(String introduced) {
			this.introducedDateBuild = introduced;
			return this;
		}

		public Builder setDiscontinuedDateBuild(String dicontinued) {
			this.discontinuedDateBuild = dicontinued;
			return this;
		}

		public Builder setCompagnyBuild(CompanyDTO companyDTO) {
			this.companyDTOBuild = companyDTO;
			return this;
		}

		public ComputerDTO build() {
			return new ComputerDTO(this);
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroducedDate() {
		return introducedDate;
	}

	public void setIntroducedDateString(String introDateString) {
		this.introducedDate = introDateString;
	}

	public String getDiscontinuedDate() {
		return discontinuedDate;
	}

	public void setDiscontinuedDateString(String discoDateString) {
		this.discontinuedDate = discoDateString;
	}

	public void setCompanyDTO(CompanyDTO companyDTO) {
		this.companyDTO = companyDTO;
	}

	public CompanyDTO getCompanyDTO() {
		return companyDTO;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
	}

}
