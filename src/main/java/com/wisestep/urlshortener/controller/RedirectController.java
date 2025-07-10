package com.wisestep.urlshortener.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.wisestep.urlshortener.entities.UrlMapping;
import com.wisestep.urlshortener.service.UrlShortenerService;

import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Validated
public class RedirectController {

	private final UrlShortenerService service;

	@GetMapping("/{shortCode}")
	public ResponseEntity<Void> redirectToOriginal(
			@PathVariable @Pattern(regexp = "^[a-zA-Z0-9]{8}$", message = "Short code must be 8 alphanumeric characters") String shortCode) {

		Optional<UrlMapping> mappingOpt = service.getOriginalUrl(shortCode);
		if (mappingOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		UrlMapping mapping = mappingOpt.get();
		if (mapping.isExpired()) {
			return ResponseEntity.status(HttpStatus.GONE).build();
		}
		service.incrementAccessCount(mapping);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create(mapping.getOriginalUrl()));
		return new ResponseEntity<>(headers, HttpStatus.FOUND);
	}
}
