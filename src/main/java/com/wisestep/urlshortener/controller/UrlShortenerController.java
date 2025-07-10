package com.wisestep.urlshortener.controller;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wisestep.urlshortener.dto.UrlShortenResponse;
import com.wisestep.urlshortener.entities.UrlMapping;
import com.wisestep.urlshortener.request.UrlRequest;
import com.wisestep.urlshortener.service.UrlShortenerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/url")
public class UrlShortenerController {

	private final UrlShortenerService service;

	@PostMapping("/shorten")
	public ResponseEntity<UrlShortenResponse> createShortUrl(@Valid @RequestBody UrlRequest request,
			HttpServletRequest servletRequest) {

		AtomicBoolean isExisting = new AtomicBoolean(false);
		UrlMapping mapping = service.createShortUrl(request.getOriginalUrl(), isExisting);

		String baseUrl = servletRequest.getScheme() + "://" + servletRequest.getServerName() + ":"
				+ servletRequest.getServerPort();

		String fullShortUrl = baseUrl + "/" + mapping.getShortCode();
		String message = isExisting.get() ? "Short URL already exists for the given original URL"
				: "Short URL generated successfully";

		return ResponseEntity.ok(new UrlShortenResponse("01", message, fullShortUrl, Instant.now()));
	}

	@GetMapping("/all")
	public ResponseEntity<List<UrlMapping>> getAllShortenedUrls() {
		return ResponseEntity.ok(service.getAllMappings());
	}
}
