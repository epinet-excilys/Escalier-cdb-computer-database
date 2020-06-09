package fr.excilys.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name ="computer")
public class Computer {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	@Column(name ="name")
	private String name;
	@Column(name = "introduced")
	private LocalDate introducedDate;
	@Column(name = "discontinued")
	private LocalDate discontinuedDate;
	@ManyToOne(fetch = FetchType.LAZY, cascade =CascadeType.ALL, targetEntity = Company.class)
	@JoinColumn(name = "company_id", referencedColumnName = "id")
	private Company company;

	private Computer(Builder builder) {
		this.id = builder.idBuild;
		this.name = builder.nameBuild;
		this.introducedDate = builder.introducedDateBuild;
		this.discontinuedDate = builder.discontinuedDateBuild;
		this.company = builder.companyBuild;
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

	public LocalDate getIntroducedDate() {
		return introducedDate;
	}

	public void setIntroducedDate(LocalDate introDate) {
		this.introducedDate = introDate;
	}

	public LocalDate getDiscontinuedDate() {
		return discontinuedDate;
	}

	public void setDiscontinuedDate(LocalDate discoDate) {
		this.discontinuedDate = discoDate;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public static class Builder {
		private int idBuild;
		private String nameBuild;
		private LocalDate introducedDateBuild;
		private LocalDate discontinuedDateBuild;
		private Company companyBuild;

		public Builder setIdBuild(int id) {
			this.idBuild = id;
			return this;
		}

		public Builder setNameBuild(String name) {
			this.nameBuild = name;
			return this;
		}

		public Builder setIntroducedDateBuild(LocalDate introduced) {
			this.introducedDateBuild = introduced;
			return this;
		}

		public Builder setDiscontinuedDateBuild(LocalDate dicontinued) {
			this.discontinuedDateBuild = dicontinued;
			return this;
		}

		public Builder setIdCompagnyBuild(Company company) {
			this.companyBuild = company;
			return this;
		}

		public Computer build() {
			return new Computer(this);
		}
	}

	

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Computer other = (Computer) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (discontinuedDate == null) {
			if (other.discontinuedDate != null)
				return false;
		} else if (!discontinuedDate.equals(other.discontinuedDate))
			return false;
		if (id != other.id)
			return false;
		if (introducedDate == null) {
			if (other.introducedDate != null)
				return false;
		} else if (!introducedDate.equals(other.introducedDate))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,ToStringStyle.JSON_STYLE);
	}

	

}
