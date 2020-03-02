package fr.excilys.dto;

import fr.excilys.model.Company;
import fr.excilys.model.Company.Builder;

public class CompanyDTO {
	

	private int id;
	private String name;
	
	
	private CompanyDTO(Builder builder) {
		this.id = builder.idBuild;
		this.name = builder.nameBuild;
	}
	
	public CompanyDTO() {
		// TODO Auto-generated constructor stub
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


	@Override
	public String toString() {
		return "CompanyDTO [id=" + id + ", name=" + name + "]";
	}
	
	

}
