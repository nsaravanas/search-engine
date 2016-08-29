package org.example.model.search;

import java.util.List;

public class Search {

	private boolean cache = true;

	private boolean index = true;

	private List<String> tags;

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public boolean isCache() {
		return cache;
	}

	public boolean getCache() {
		return cache;
	}

	public void setCache(boolean cache) {
		this.cache = cache;
	}

	public boolean isIndex() {
		return index;
	}

	public boolean getIndex() {
		return index;
	}

	public void setIndex(boolean index) {
		this.index = index;
	}

}
