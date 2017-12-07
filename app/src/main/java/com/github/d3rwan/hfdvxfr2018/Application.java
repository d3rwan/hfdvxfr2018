package com.github.d3rwan.hfdvxfr2018;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.d3rwan.hfdvxfr2018.ad.AdRepository;
import com.github.d3rwan.hfdvxfr2018.ad.AdRestTemplateRepository;
import com.github.d3rwan.hfdvxfr2018.news.*;
import com.github.d3rwan.hfdvxfr2018.weather.WeatherRepository;
import com.github.d3rwan.hfdvxfr2018.weather.WeatherRestTemplateRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper mapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return mapper;
    }

    @Bean
    public ArticleRepository articleRepository(ApplicationProperties properties) {
        return new ArticleInMemoryRepository(properties.getArticles());
    }

    @Bean
    public AdRepository adRepository(RestTemplate restTemplate,
                                     ApplicationProperties properties) {
        return new AdRestTemplateRepository(
                restTemplate,
                properties.getAd().getUrl()
        );
    }

    @Bean
    public WeatherRepository weatherRepository(RestTemplate restTemplate,
                                               ApplicationProperties properties) {
        return new WeatherRestTemplateRepository(
                restTemplate,
                properties.getWeather().getUrl()
        );
    }

    @Bean
    public NewsService newsService(ArticleRepository articleRepository,
                                   AdRepository adRepository,
                                   WeatherRepository weatherRepository) {
        return new NewsService(
                articleRepository,
                adRepository,
                weatherRepository
        );
    }

    @Bean
    public NewsResource newsResource(NewsService newsService) {
        return new NewsResource(newsService);
    }

}