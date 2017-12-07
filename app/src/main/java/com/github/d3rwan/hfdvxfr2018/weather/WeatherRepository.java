package com.github.d3rwan.hfdvxfr2018.weather;

public interface WeatherRepository {
    WeatherInformation findWeatherForCity(String city);
}
