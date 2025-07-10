package com.wisestep.urlshortener.util;

import java.net.HttpURLConnection;
import java.net.URL;

public class UrlValidator {

	private UrlValidator() {
		throw new UnsupportedOperationException("Utility class should not be instantiated");
	}

	public static boolean isUrlReachable(String urlString) {
		try {
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(3000);
			connection.setReadTimeout(3000);
			connection.setRequestMethod("HEAD");
			int responseCode = connection.getResponseCode();
			return responseCode >= 200 && responseCode < 400;
		} catch (Exception e) {
			return false;
		}
	}
}
