package com.github.d3rwan.hfdvxfr2018.news;

import com.github.d3rwan.hfdvxfr2018.ad.Ad;
import com.github.d3rwan.hfdvxfr2018.ad.AdRepository;
import com.github.d3rwan.hfdvxfr2018.weather.WeatherInformation;
import com.github.d3rwan.hfdvxfr2018.weather.WeatherRepository;

import java.util.List;

public class NewsService {
    private final ArticleRepository articleRepository;
    private final AdRepository adRepository;
    private final WeatherRepository weatherRepository;

    public NewsService(ArticleRepository articleRepository,
                       AdRepository adRepository,
                       WeatherRepository weatherRepository) {
        this.articleRepository = articleRepository;
        this.adRepository = adRepository;
        this.weatherRepository = weatherRepository;
    }

    public News getNewsForCity(String city) {
        List<Article> articles = articleRepository.findLastArticleForCity(city);
        WeatherInformation weather = weatherRepository.findWeatherForCity(city);
        List<Ad> ads = adRepository.findAdsForCity(city);
        return new News(articles, weather, ads);
    }
}
