package com.example.weather.data.openweather;

import com.example.weather.data.ApiErrorException;
import com.example.weather.data.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Wrapper of Openweather API able to retrieve temperature of a given city.
 * Unlike {@link com.example.weather.data.WeatherInfoService} it can't cache results
 */
@Service
@ConfigurationProperties(prefix="ow")
class OpenweatherApi {
    private static final Logger log = LoggerFactory.getLogger(OpenweatherApi.class);
    private static final String url =
            "https://api.openweathermap.org/data/2.5/weather?units=metric&q={city}&appid={apikey}";

    private String apikey;

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public int getTemperature(String city) throws NotFoundException, ApiErrorException {
        RestTemplate restTemplate = new RestTemplate();

        try {
            OpenWeatherResponse openWeatherResponse = restTemplate.getForObject(url,
                    OpenWeatherResponse.class, city, apikey);
            log.trace("{}", openWeatherResponse);

            return (int) openWeatherResponse.getTemp();
        }
        catch (HttpClientErrorException e) {
            if (e instanceof HttpClientErrorException.NotFound)
                throw new NotFoundException(e);
            else
                throw new ApiErrorException(e);
        }
    }
}
