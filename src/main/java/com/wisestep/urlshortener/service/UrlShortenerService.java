package com.wisestep.urlshortener.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import com.wisestep.urlshortener.entities.UrlMapping;

public interface UrlShortenerService {
	public UrlMapping createShortUrl(String originalUrl, AtomicBoolean isExisting);

	public Optional<UrlMapping> getOriginalUrl(String shortCode);

	public void incrementAccessCount(UrlMapping mapping);

	public List<UrlMapping> getAllMappings();
}
