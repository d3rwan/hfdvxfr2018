package com.github.d3rwan.hfdvxfr2018.weather;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;

public class WeatherRestTemplateRepository implements WeatherRepository {
    private static Logger LOGGER = LoggerFactory.getLogger(WeatherRestTemplateRepository.class);

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public WeatherRestTemplateRepository(RestTemplate restTemplate,
                                         String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    @Override
    public WeatherInformation findWeatherForCity(String city) {
        LOGGER.info("appel au service Weather pour la ville {}", city);
        Instant start = Instant.now();
        String url = String.format("%s/api/weather/%s", baseUrl, city);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<WeatherInformation> resp = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<WeatherInformation>() {}
        );
        Instant end = Instant.now();
        LOGGER.debug("le service a répondu {}", resp.toString());
        LOGGER.info("le service a répondu en {}ms", Duration.between(start, end).toMillis());
        return resp.getBody();
    }
}
