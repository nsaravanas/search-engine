package org.example.service;

import java.util.List;
import java.util.Map;

import org.example.model.Page;
import org.example.model.search.Search;

public interface SearchService {

	List<Page> search(Search search);

	List<Page> save(List<Page> pages);

	boolean delete(List<String> pages);

	void reIndex();

	List<Page> getAll();

	Map<String, Boolean> clear();

	void updatePageStats(List<Page> pages);

}
