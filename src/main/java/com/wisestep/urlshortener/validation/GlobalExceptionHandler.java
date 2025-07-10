package com.wisestep.urlshortener.validation;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.wisestep.urlshortener.dto.UrlShortenResponse;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<UrlShortenResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
		String errors = ex.getBindingResult().getAllErrors().stream().map(error -> error.getDefaultMessage())
				.collect(Collectors.joining(", "));

		UrlShortenResponse response = new UrlShortenResponse("02", errors, null, Instant.now());
		return ResponseEntity.badRequest().body(response);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<UrlShortenResponse> handleConstraintViolation(ConstraintViolationException ex) {
		String errors = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
				.collect(Collectors.joining(", "));

		UrlShortenResponse response = new UrlShortenResponse("02", errors, null, Instant.now());
		return ResponseEntity.badRequest().body(response);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<UrlShortenResponse> handleGenericException(Exception ex) {
		UrlShortenResponse response = new UrlShortenResponse("03", ex.getMessage(), null, Instant.now());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
}
