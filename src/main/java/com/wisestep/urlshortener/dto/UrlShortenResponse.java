package com.wisestep.urlshortener.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UrlShortenResponse {
	private String scode;
	private String sdesc;
	private String shortUrl;
	private Instant timestamp;
}