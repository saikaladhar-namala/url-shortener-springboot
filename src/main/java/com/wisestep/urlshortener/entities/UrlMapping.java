package com.wisestep.urlshortener.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "url_mappings")
@Data
@NoArgsConstructor
public class UrlMapping {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mappingId;

	@Column(nullable = false, columnDefinition = "TEXT", unique = true)
	private String originalUrl;

	@Column(nullable = false, unique = true, length = 10)
	private String shortCode;

	@Column(nullable = false, updatable = false)
	private LocalDateTime creationTime;

	@Column(nullable = false, updatable = false)
	private LocalDateTime expiryTime;

	@Column(nullable = false)
	private int hitCount = 0;

	public static UrlMapping createWithCode(String originalUrl, String shortCode) {
		UrlMapping mapping = new UrlMapping();
		mapping.setOriginalUrl(originalUrl);
		mapping.setCreationTime(LocalDateTime.now());
		mapping.setExpiryTime(mapping.getCreationTime().plusMinutes(5));
		mapping.setShortCode(shortCode); 
		mapping.setHitCount(0);
		return mapping;
	}

	public void incrementHitCount() {
		this.hitCount++;
	}

	public boolean isExpired() {
		return LocalDateTime.now().isAfter(expiryTime);
	}
}
