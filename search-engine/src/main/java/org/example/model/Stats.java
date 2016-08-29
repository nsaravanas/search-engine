package org.example.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Stats {

	@Id
	@GeneratedValue
	private Long id;

	@Column
	private LocalDateTime lastVisit;

	@Column
	private long totalVisit;

	@ElementCollection
	@JsonIgnore
	private List<String> missingTags;

	public List<String> getMissingTags() {
		return missingTags;
	}

	public void setMissingTags(List<String> missingTags) {
		this.missingTags = missingTags;
	}

	public LocalDateTime getLastVisit() {
		return lastVisit;
	}

	public long getTotalVisit() {
		return totalVisit;
	}

	public void incrementTotalVisit() {
		this.totalVisit = getTotalVisit() + 1;
	}

	public Long getId() {
		return id;
	}

	public void setLastVisit(LocalDateTime lastVisit) {
		this.lastVisit = lastVisit;
	}

	public void setLastVisitToNow() {
		this.lastVisit = LocalDateTime.now();
	}

	public void setTotalVisit(long totalVisit) {
		this.totalVisit = totalVisit;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
