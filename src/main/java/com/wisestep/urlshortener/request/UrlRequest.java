package com.wisestep.urlshortener.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UrlRequest {

    @NotBlank(message = "Original URL cannot be blank")
    private String originalUrl;
}

