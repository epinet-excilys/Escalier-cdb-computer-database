package fr.excilys.dto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.stereotype.Component;

@Component
public class CompanyDTO {
	

	private int id;
	private String name;
	
	
	private CompanyDTO(Builder builder) {
		this.id = builder.idBuild;
		this.name = builder.nameBuild;
	}
	
	public CompanyDTO() {

	}

	public static class Builder {
		private int idBuild;
		private String nameBuild;

		public Builder() {
		}

		public Builder setIdBuild(int idBDD) {
			this.idBuild = idBDD;
			return this;
		}

		public Builder setNameBuild(String nameBDD) {
			this.nameBuild = nameBDD;
			return this;
		}

		public CompanyDTO build() {
			return new CompanyDTO(this);
		}
	}
	
	


	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,ToStringStyle.JSON_STYLE);
	}

	
	

}
