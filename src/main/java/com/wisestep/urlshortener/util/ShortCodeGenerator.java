package com.wisestep.urlshortener.util;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ShortCodeGenerator {

	private final Random random = new SecureRandom();

	@Value("${shortcode.length}")
	private int shortCodeLength;

	@Value("${shortcode.charset}")
	private String base62Charset;

	public String generateRandomShortCode() {
		StringBuilder sb = new StringBuilder(shortCodeLength);
		for (int i = 0; i < shortCodeLength; i++) {
			sb.append(base62Charset.charAt(random.nextInt(base62Charset.length())));
		}
		return sb.toString();
	}
}
