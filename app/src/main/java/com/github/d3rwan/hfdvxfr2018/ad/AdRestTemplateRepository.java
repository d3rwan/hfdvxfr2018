package com.github.d3rwan.hfdvxfr2018.ad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class AdRestTemplateRepository implements AdRepository {
    private static Logger LOGGER = LoggerFactory.getLogger(AdRestTemplateRepository.class);

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public AdRestTemplateRepository(RestTemplate restTemplate,
                                    String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    @Override
    public List<Ad> findAdsForCity(String city) {
        LOGGER.info("appel au service AD pour la ville {}", city);
        Instant start = Instant.now();
        String url = String.format("%s/api/ad/%s", baseUrl, city);
        ResponseEntity<List<Ad>> resp = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Ad>>() {}
        );
        Instant end = Instant.now();
        LOGGER.debug("le service a répondu {}", resp.toString());
        LOGGER.info("le service a répondu en {}ms", Duration.between(start, end).toMillis());
        return resp.getBody();
    }
}
