package org.example.service;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.example.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SearchEngineImpl implements SearchEngine {

	@Value("${max.weight}")
	private int maxWeight;

	@Autowired
	private SearchEngineOptimization engineOptimization;

	public void setEngineOptimization(SearchEngineOptimization engineOptimization) {
		this.engineOptimization = engineOptimization;
	}

	@Override
	public SearchEngineOptimization getEngineOptimization() {
		return engineOptimization;
	}

	@Override
	public Map<String, List<Page>> indexing(List<Page> pages, List<List<String>> queries) {
		Map<String, List<Page>> indexed = new LinkedHashMap<>();
		for (List<String> queryTags : queries) {
			String queryString = getQueryString(queryTags);
			indexed.put(queryString, indexing(pages, queryTags, queryString));
		}
		return indexed;
	}

	@Override
	public List<Page> indexing(List<Page> pages, String[] query) {
		return indexing(pages, Arrays.asList(query), SearchEngineImpl.getQueryString(query));
	}

	@Override
	public List<Page> indexing(List<Page> pages, List<String> query, String queryString) {
		List<Page> matchedPages = new ArrayList<>();
		for (Page page : pages) {
			page.setWeight(calculateWeight(query, page.getTags()));
			if (page.getWeight() != 0)
				matchedPages.add(page);
		}
		matchedPages.sort(Comparator.comparingInt(Page::getWeight).reversed());
		addToCache(queryString, matchedPages);
		return matchedPages;
	}

	@Override
	public void addToCache(String queryString, List<Page> matchedPages) {
		this.engineOptimization.addToCache(queryString, matchedPages);
	}

	@Override
	public int calculateWeight(List<String> queryTags, List<String> pageTags) {
		final AtomicInteger ai = new AtomicInteger(maxWeight);
		Map<String, Integer> queryMap = queryTags.stream().distinct().limit(maxWeight).collect(toMap(tag -> tag, value -> ai.getAndDecrement()));
		ai.set(maxWeight);
		Map<String, Integer> pageMap = pageTags.stream().distinct().limit(maxWeight).collect(toMap(tag -> tag, value -> ai.getAndDecrement()));
		return queryMap.keySet().stream().filter(pageMap::containsKey).mapToInt(key -> queryMap.get(key) * pageMap.get(key)).sum();
	}

	@Deprecated
	public int calculate(List<String> queryTags, List<String> pageTags) {
		int weight = 0;
		int queryWeight = maxWeight;
		for (String queryTag : queryTags) {
			int pageWeight = maxWeight;
			for (String pageTag : pageTags) {
				if (pageTag.equalsIgnoreCase(queryTag)) {
					weight += pageWeight * queryWeight;
				}
				pageWeight--;
			}
			queryWeight--;
		}
		return weight;
	}

	public static String getQueryString(List<String> queryList) {
		return queryList.stream().collect(joining("_")).toLowerCase();
	}

	public static String getQueryString(String[] queryList) {
		return getQueryString(Arrays.asList(queryList));
	}
}
