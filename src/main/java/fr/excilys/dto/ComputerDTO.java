package fr.excilys.dto;

public class ComputerDTO {

	private int id;
	private String name;
	private String introducedDate;
	private String discontinuedDate;
	private CompanyDTO company;

	public ComputerDTO(String name, String introDateString, String discoDateString, CompanyDTO companyDTO) {
		this.name = name;
		this.introducedDate = introDateString;
		this.discontinuedDate = discoDateString;
		this.company = companyDTO;
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

	public String getIntroducedDateString() {
		return introducedDate;
	}

	public void setIntroducedDateString(String introDateString) {
		this.introducedDate = introDateString;
	}

	public String getDiscontinuedDateString() {
		return discontinuedDate;
	}

	public void setDiscontinuedDateString(String discoDateString) {
		this.discontinuedDate = discoDateString;
	}

	public CompanyDTO getCompanyDTO() {
		return company;
	}

	public void setCompanyDTO(CompanyDTO companyDTO) {
		this.company = companyDTO;
	}

	@Override
	public String toString() {
		return "ComputerDTO [id=" + id + ", name=" + name + ", introducedDate=" + introducedDate + ", discontinuedDate="
				+ discontinuedDate + ", company=" + company + "]";
	}

}
