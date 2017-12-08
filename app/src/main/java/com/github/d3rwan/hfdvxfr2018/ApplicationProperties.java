package com.github.d3rwan.hfdvxfr2018;

import com.github.d3rwan.hfdvxfr2018.news.Article;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties
public class ApplicationProperties {

    private RemoteService weather;
    private RemoteService ad;
    private Map<String, List<Article>> articles = new HashMap<>();

    public RemoteService getWeather() {
        return weather;
    }

    public void setWeather(RemoteService weather) {
        this.weather = weather;
    }

    public RemoteService getAd() {
        return ad;
    }

    public void setAd(RemoteService ad) {
        this.ad = ad;
    }

    public Map<String, List<Article>> getArticles() {
        return articles;
    }

    public void setArticles(Map<String, List<Article>> articles) {
        this.articles = articles;
    }

    public static class RemoteService {
        private String url;
        private Integer executionTimeoutInMilliseconds;
        private Integer circuitBreakerRequestVolumeThreshold;
        private Integer circuitBreakerSleepWindowInMilliseconds;
        private Integer circuitBreakerErrorThresholdPercentage;
        private Integer metricsRollingPercentileWindowInMilliseconds;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Integer getExecutionTimeoutInMilliseconds() {
            return executionTimeoutInMilliseconds;
        }

        public void setExecutionTimeoutInMilliseconds(Integer executionTimeoutInMilliseconds) {
            this.executionTimeoutInMilliseconds = executionTimeoutInMilliseconds;
        }

        public Integer getCircuitBreakerRequestVolumeThreshold() {
            return circuitBreakerRequestVolumeThreshold;
        }

        public void setCircuitBreakerRequestVolumeThreshold(Integer circuitBreakerRequestVolumeThreshold) {
            this.circuitBreakerRequestVolumeThreshold = circuitBreakerRequestVolumeThreshold;
        }

        public Integer getCircuitBreakerSleepWindowInMilliseconds() {
            return circuitBreakerSleepWindowInMilliseconds;
        }

        public void setCircuitBreakerSleepWindowInMilliseconds(Integer circuitBreakerSleepWindowInMilliseconds) {
            this.circuitBreakerSleepWindowInMilliseconds = circuitBreakerSleepWindowInMilliseconds;
        }

        public Integer getCircuitBreakerErrorThresholdPercentage() {
            return circuitBreakerErrorThresholdPercentage;
        }

        public void setCircuitBreakerErrorThresholdPercentage(Integer circuitBreakerErrorThresholdPercentage) {
            this.circuitBreakerErrorThresholdPercentage = circuitBreakerErrorThresholdPercentage;
        }

        public Integer getMetricsRollingPercentileWindowInMilliseconds() {
            return metricsRollingPercentileWindowInMilliseconds;
        }

        public void setMetricsRollingPercentileWindowInMilliseconds(Integer metricsRollingPercentileWindowInMilliseconds) {
            this.metricsRollingPercentileWindowInMilliseconds = metricsRollingPercentileWindowInMilliseconds;
        }
    }
}
