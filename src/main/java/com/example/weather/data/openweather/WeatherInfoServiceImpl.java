package com.example.weather.data.openweather;

import com.example.weather.data.NotFoundException;
import com.example.weather.data.WeatherInfo;
import com.example.weather.data.ApiErrorException;
import com.example.weather.data.WeatherInfoService;
import org.springframework.stereotype.Service;

import org.apache.commons.collections4.map.LRUMap;

import java.util.Collections;
import java.util.Map;

/**
 * An implementation of {@link WeatherInfoService} able to cache results
 * @see WeatherInfoService
 */
@Service
class WeatherInfoServiceImpl implements WeatherInfoService {
    private static final long CACHE_INVALIDATION_PERIOD = 15 * 60 * 1000; // 15 minutes

    /** Cache for 1000 last results */
    private final Map<String, WeatherInfo> cache = Collections.synchronizedMap(new LRUMap<>(1000));

    private final OpenweatherApi openweatherApi;

    public WeatherInfoServiceImpl(OpenweatherApi openweatherApi) {
        this.openweatherApi = openweatherApi;
    }

    public WeatherInfo getWeatherInfo(String city) throws NotFoundException, ApiErrorException {
        WeatherInfo cachedInfo = cache.get(city);

        if (cachedInfo != null && (System.currentTimeMillis() - cachedInfo.getCreated() < CACHE_INVALIDATION_PERIOD))
            return cachedInfo;

        int temperature = openweatherApi.getTemperature(city);
        final WeatherInfo result = new WeatherInfo(city, temperature, System.currentTimeMillis());
        cache.put(city, result);

        return result;
    }
}
