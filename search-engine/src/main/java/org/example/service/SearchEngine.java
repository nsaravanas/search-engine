package org.example.service;

import java.util.List;
import java.util.Map;

import org.example.model.Page;

public interface SearchEngine {

	Map<String, List<Page>> indexing(List<Page> pages, List<List<String>> queries);

	List<Page> indexing(List<Page> pages, List<String> query, String queryString);

	void addToCache(String queryString, List<Page> matchedPages);

	int calculateWeight(List<String> queryTags, List<String> pageTags);

	SearchEngineOptimization getEngineOptimization();

	List<Page> indexing(List<Page> pages, String[] query);

}
