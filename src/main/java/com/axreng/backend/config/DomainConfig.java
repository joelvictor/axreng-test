package com.axreng.backend.config;

import com.axreng.backend.domain.exception.InvalidDomainException;

import java.net.URI;
import java.net.URISyntaxException;

public class DomainConfig {

    private String baseUrl;

    public String getBaseUrl() throws InvalidDomainException {
        if (baseUrl == null) {
            baseUrl = System.getenv("BASE_URL");
            try {
                new URI(baseUrl);
            } catch (URISyntaxException | NullPointerException e) {
                throw new InvalidDomainException();
            }
        }
        return baseUrl;
    }

}
