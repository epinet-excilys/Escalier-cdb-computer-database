package fr.excilys.dto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.web.bind.annotation.RequestParam;

public class DashBoardParameterDTO {
	private String pageIterator;
	private String NbRowComputer;
	private String maxPage;
	private String pageSize;
	private String order;
	private String search;
	private String errorMessage;
	private String successMessage;
	
	
	
	public String getPageIterator() {
		return pageIterator;
	}
	
	public String getNbRowComputer() {
		return NbRowComputer;
	}
	
	public String getMaxPage() {
		return maxPage;
	}
	
	public String getPageSize() {
		return pageSize;
	}
	
	public String getOrder() {
		return order;
	}
	
	public String getSearch() {
		return search;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public String getSuccessMessage() {
		return successMessage;
	}
	
	public void setPageIterator(String pageIterator) {
		this.pageIterator = pageIterator;
	}
	
	public void setNbRowComputer(String nbRowComputer) {
		NbRowComputer = nbRowComputer;
	}
	
	public void setMaxPage(String maxPage) {
		this.maxPage = maxPage;
	}
	
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	
	public void setOrder(String order) {
		this.order = order;
	}
	
	public void setSearch(String search) {
		this.search = search;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,ToStringStyle.JSON_STYLE);
	}
	

}
