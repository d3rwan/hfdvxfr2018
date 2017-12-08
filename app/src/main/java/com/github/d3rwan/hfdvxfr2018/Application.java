package com.github.d3rwan.hfdvxfr2018;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.d3rwan.hfdvxfr2018.ad.AdFeignRepository;
import com.github.d3rwan.hfdvxfr2018.ad.AdRepository;
import com.github.d3rwan.hfdvxfr2018.news.ArticleInMemoryRepository;
import com.github.d3rwan.hfdvxfr2018.news.ArticleRepository;
import com.github.d3rwan.hfdvxfr2018.news.NewsResource;
import com.github.d3rwan.hfdvxfr2018.news.NewsService;
import com.github.d3rwan.hfdvxfr2018.weather.WeatherRepository;
import com.github.d3rwan.hfdvxfr2018.weather.WeatherResilientRestTemplateRepository;
import com.github.d3rwan.hfdvxfr2018.weather.WeatherRestTemplateRepository;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.hystrix.HystrixFeign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

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
    public AdRepository adRepository(ApplicationProperties properties,
                                     Encoder encoder,
                                     Decoder decoder) {
        ApplicationProperties.RemoteService adProperties = properties.getAd();
        AdFeignRepository fallback = city -> Collections.emptyList();
        return HystrixFeign.builder()
                .encoder(encoder)
                .decoder(decoder)
                .setterFactory((target, method) -> HystrixCommand.Setter
                        .withGroupKey(HystrixCommandGroupKey.Factory.asKey("ads"))
                        .andCommandPropertiesDefaults(
                                HystrixCommandProperties.Setter()
                                        .withExecutionTimeoutInMilliseconds(adProperties.getExecutionTimeoutInMilliseconds())
                                        .withCircuitBreakerRequestVolumeThreshold(adProperties.getCircuitBreakerRequestVolumeThreshold())
                                        .withCircuitBreakerSleepWindowInMilliseconds(adProperties.getCircuitBreakerSleepWindowInMilliseconds())
                                        .withCircuitBreakerErrorThresholdPercentage(adProperties.getCircuitBreakerErrorThresholdPercentage())
                                        .withMetricsRollingPercentileWindowInMilliseconds(adProperties.getMetricsRollingPercentileWindowInMilliseconds())))
                .target(AdFeignRepository.class, adProperties.getUrl(), fallback);
    }

    @Bean
    public Decoder decoder() {
        return new JacksonDecoder();
    }

    @Bean
    public Encoder encoder() {
        return new JacksonEncoder();
    }

    @Bean
    public WeatherRepository weatherRepository(RestTemplate restTemplate,
                                               ApplicationProperties properties) {
        ApplicationProperties.RemoteService weatherProperties = properties.getWeather();
        WeatherRestTemplateRepository weatherRestTemplateRepository = new WeatherRestTemplateRepository(
                restTemplate,
                weatherProperties.getUrl()
        );
        HystrixCommand.Setter circuitBreakerConfig = HystrixCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("weather"))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                .withExecutionTimeoutInMilliseconds(weatherProperties.getExecutionTimeoutInMilliseconds())
                                .withCircuitBreakerRequestVolumeThreshold(weatherProperties.getCircuitBreakerRequestVolumeThreshold())
                                .withCircuitBreakerSleepWindowInMilliseconds(weatherProperties.getCircuitBreakerSleepWindowInMilliseconds())
                                .withCircuitBreakerErrorThresholdPercentage(weatherProperties.getCircuitBreakerErrorThresholdPercentage())
                                .withMetricsRollingPercentileWindowInMilliseconds(weatherProperties.getMetricsRollingPercentileWindowInMilliseconds())
                );
        return new WeatherResilientRestTemplateRepository(
                weatherRestTemplateRepository,
                circuitBreakerConfig
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

    @Bean(name = "hystrixRegistrationBean")
    public ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean registration = new ServletRegistrationBean(
                new HystrixMetricsStreamServlet(),
                "/metrics/hystrix.stream"
        );
        registration.setName("hystrixServlet");
        registration.setLoadOnStartup(1);
        return registration;
    }

}