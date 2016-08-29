package org.example.service;

import static java.util.Comparator.comparing;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.example.model.Page;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SearchEngineOptimizationImpl implements SearchEngineOptimization {

	@Value("${cache.size}")
	private int cacheSize;

	private final static Map<Key, List<Page>> CACHE = new WeakHashMap<>();

	@Override
	public Map<Key, List<Page>> getCache() {
		return CACHE;
	}

	@Override
	public void addToCache(String queryString, List<Page> matchedPages) {
		if (CACHE.size() >= cacheSize) {
			removeOldQueriesFromCache();
		}
		CACHE.put(new Key(queryString, LocalDateTime.now()), matchedPages);
	}

	@Override
	public List<Page> getFromCache(String queryString) {
		return CACHE.get(new Key(queryString));
	}

	@Scheduled(fixedDelay = 15 * 60 * 1000) // remove old entries from cache
	public void removeOldQueriesFromCache() {
		CACHE.keySet().stream().sorted(comparing(Key::getLastAccessed)).limit(5).forEach(CACHE::remove);
	}

}
