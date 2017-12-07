package com.github.d3rwan.hfdvxfr2018.news;

import com.github.d3rwan.hfdvxfr2018.ad.Ad;
import com.github.d3rwan.hfdvxfr2018.weather.WeatherInformation;

import java.util.List;

public class News {
    private List<Article> articles;
    private WeatherInformation weather;
    private List<Ad> ads;

    public News() {
    }

    public News(List<Article> articles,
                WeatherInformation weather,
                List<Ad> ads) {
        this.articles = articles;
        this.weather = weather;
        this.ads = ads;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public WeatherInformation getWeather() {
        return weather;
    }

    public void setWeather(WeatherInformation weather) {
        this.weather = weather;
    }

    public List<Ad> getAds() {
        return ads;
    }

    public void setAds(List<Ad> ads) {
        this.ads = ads;
    }
}
