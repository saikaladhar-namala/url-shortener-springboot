package com.wisestep.urlshortener.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wisestep.urlshortener.entities.UrlMapping;
import com.wisestep.urlshortener.repository.UrlMappingRepository;
import com.wisestep.urlshortener.service.UrlShortenerService;
import com.wisestep.urlshortener.util.ShortCodeGenerator;
import com.wisestep.urlshortener.util.UrlValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UrlShortenerServiceImpl implements UrlShortenerService {

	private final UrlMappingRepository repository;
	private final ShortCodeGenerator shortCodeGenerator;

	@Override
	@Transactional
	public UrlMapping createShortUrl(String originalUrl, AtomicBoolean isExisting) {

		// Validating URL
		if (!UrlValidator.isUrlReachable(originalUrl)) {
			throw new IllegalArgumentException("Provided URL is not reachable: " + originalUrl);
		}

		Optional<UrlMapping> existing = repository.findByOriginalUrl(originalUrl);
		if (existing.isPresent()) {
			isExisting.set(true);
			return existing.get();
		}
		final int MAX_ATTEMPTS = 5;
		String shortCode = null;
		for (int i = 0; i < MAX_ATTEMPTS; i++) {
			String candidate = shortCodeGenerator.generateRandomShortCode();
			if (!repository.findByShortCode(candidate).isPresent()) {
				shortCode = candidate;
				break;
			}
		}
		if (shortCode == null) {
			throw new IllegalStateException("Failed to generate a unique short code. Please try again.");
		}

		UrlMapping newMapping = UrlMapping.createWithCode(originalUrl, shortCode);
		return repository.save(newMapping);
	}

	@Override
	public Optional<UrlMapping> getOriginalUrl(String shortCode) {
		return repository.findByShortCode(shortCode);
	}

	@Override
	public void incrementAccessCount(UrlMapping mapping) {
		mapping.incrementHitCount();
		repository.save(mapping);
	}

	@Override
	public List<UrlMapping> getAllMappings() {
		return repository.findAllByOrderByCreationTimeDesc();
	}
}
