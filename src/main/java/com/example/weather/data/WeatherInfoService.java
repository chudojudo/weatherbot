package com.example.weather.data;

/**
 * Interface to get data about temperature in a given city
 */
public interface WeatherInfoService {
    WeatherInfo getWeatherInfo(String city) throws NotFoundException, ApiErrorException;
}
