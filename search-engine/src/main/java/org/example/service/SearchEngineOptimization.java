package org.example.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.example.model.Page;

public interface SearchEngineOptimization {

	void addToCache(String queryString, List<Page> matchedPages);

	Map<Key, List<Page>> getCache();

	List<Page> getFromCache(String queryString);

	// To find the aged query
	final static class Key {

		private String query;

		private LocalDateTime lastAccessed;

		public Key() {
			super();
		}

		public Key(String query) {
			super();
			this.query = query;
		}

		public Key(String query, LocalDateTime lastAccessed) {
			super();
			this.query = query;
			this.lastAccessed = lastAccessed;
		}

		public String getQuery() {
			return query;
		}

		public LocalDateTime getLastAccessed() {
			return lastAccessed;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((query == null) ? 0 : query.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Key other = (Key) obj;
			if (query == null) {
				if (other.query != null)
					return false;
			} else if (!query.equals(other.query))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Key [query=" + query + ", lastAccessed=" + lastAccessed + "]";
		}

	}

}
