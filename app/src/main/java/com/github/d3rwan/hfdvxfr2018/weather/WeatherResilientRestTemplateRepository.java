package com.github.d3rwan.hfdvxfr2018.weather;

import com.netflix.hystrix.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class WeatherResilientRestTemplateRepository implements WeatherRepository {
    private static Logger LOGGER = LoggerFactory.getLogger(WeatherResilientRestTemplateRepository.class);

    private final WeatherRestTemplateRepository weatherRestTemplateRepository;
    private Map<String, WeatherInformation> lastKnownWeatherForCity;
    private HystrixCommand.Setter circuitBreakerConfig;

    public WeatherResilientRestTemplateRepository(WeatherRestTemplateRepository weatherRestTemplateRepository,
                                                  HystrixCommand.Setter circuitBreakerConfig) {
        this.weatherRestTemplateRepository = weatherRestTemplateRepository;
        this.lastKnownWeatherForCity = new HashMap<>();
        this.lastKnownWeatherForCity.put("paris", new WeatherInformation("Clear", 27));
        this.circuitBreakerConfig = circuitBreakerConfig;
    }

    @Override
    public WeatherInformation findWeatherForCity(String city) {
        return new WeatherCommand(
                () -> weatherRestTemplateRepository.findWeatherForCity(city),
                () -> lastKnownWeatherForCity.get(city),
                circuitBreakerConfig
        ).execute();
    }

    private class WeatherCommand extends HystrixCommand<WeatherInformation> {
        private final Supplier<WeatherInformation> runCmd;
        private final Supplier<WeatherInformation> fallbackCmd;

        protected WeatherCommand(Supplier<WeatherInformation> runCommand,
                                 Supplier<WeatherInformation> fallbackCmd,
                                 Setter config) {
            super(config);
            this.runCmd = runCommand;
            this.fallbackCmd = fallbackCmd;
        }

        @Override
        protected WeatherInformation run() throws Exception {
            return runCmd.get();
        }

        @Override
        protected WeatherInformation getFallback() {
            return fallbackCmd.get();
        }
    }

}