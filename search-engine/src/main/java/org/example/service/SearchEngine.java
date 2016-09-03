package org.example.service;

import java.util.List;

import org.example.model.Page;

public interface SearchEngine {

	List<Page> indexing(List<Page> pages, List<String> query, String queryString);

	void addToCache(String queryString, List<Page> matchedPages);

	int calculateWeight(List<String> queryTags, List<String> pageTags);

	SearchEngineOptimization getEngineOptimization();

	List<Page> indexing(List<Page> pages, String[] query);

}
