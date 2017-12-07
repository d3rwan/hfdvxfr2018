package com.github.d3rwan.hfdvxfr2018.weather;

public class WeatherInformation {
    private String type;
    private Integer temperature;

    public WeatherInformation() {
    }

    public WeatherInformation(String type, Integer temperature) {
        this.type = type;
        this.temperature = temperature;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }
}
