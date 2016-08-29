package org.example.model;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;

@Entity
public class QueryResult {

	@Id
	private String query;

	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> pageNames;

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public List<String> getPageNames() {
		return pageNames;
	}

	public void setPageNames(List<String> pageNames) {
		this.pageNames = pageNames;
	}

}
