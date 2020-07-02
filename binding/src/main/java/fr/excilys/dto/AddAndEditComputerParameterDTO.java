package fr.excilys.dto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.stereotype.Component;

@Component
public class AddAndEditComputerParameterDTO {
	private String computerId;
	private String computerName;
	private String introduced;
	private String discontinued;
	private String companyId;
		
	public String getComputerId() {
		return computerId;
	}

	public String getComputerName() {
		return computerName;
	}

	public String getIntroduced() {
		return introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setComputerId(String computerId) {
		this.computerId = computerId;
	}

	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,ToStringStyle.JSON_STYLE);
	}

}
