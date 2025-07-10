package com.wisestep.urlshortener.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wisestep.urlshortener.entities.UrlMapping;

@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {

	Optional<UrlMapping> findByShortCode(String shortCode);

	Optional<UrlMapping> findByOriginalUrl(String originalUrl);

	List<UrlMapping> findAllByOrderByCreationTimeDesc();
}
