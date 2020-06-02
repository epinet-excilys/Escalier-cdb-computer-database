package fr.excilys.model;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Company {

	private int id;
	private String name;

	private Company(Builder builder) {
		this.id = builder.idBuild;
		this.name = builder.nameBuild;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
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

		public Company build() {
			return new Company(this);
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
		Company other = (Company) obj;
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
