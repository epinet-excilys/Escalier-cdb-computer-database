package fr.excilys.dto;

import org.springframework.stereotype.Service;

@Service
public class RestControllerParameter {
	
	private String search;
	
	private int page;
	private int size;
	
	
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	

	
}
