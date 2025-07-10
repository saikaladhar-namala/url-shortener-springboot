# URL Shortener - Spring Boot

A simple URL Shortening service built using **Spring Boot**, with RESTful APIs and redirect support.

## Features

- Shorten long URLs
- Redirect to original URL using the short code
- Track hit count for each shortened URL
- Detect and handle expired links

## Tech Stack

- Java 17+
- Spring Boot 3.x
- Spring Data JPA 
- Lombok
- Hibernate Validator
- REST APIs

## How to Run Locally

1. **Clone the Repository**

```bash
git clone https://github.com/saikaladhar-namala/url-shortener-springboot.git
cd url-shortener-springboot
```

2. **Open in your IDE** (like Spring Tool Suite or IntelliJ)

3. **Run the application**

- Run `UrlShortenerApplication.java` from your IDE.
- Or run from terminal:

```bash
./mvnw spring-boot:run
```

4. **Access APIs:**

- POST `/api/v1/url/shorten` – Shorten a URL
- GET `/{shortCode}` – Redirect to original URL
- GET `/api/v1/url/all` – List all shortened URLs

