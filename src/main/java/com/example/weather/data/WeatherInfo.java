package com.example.weather.data;

/**
 * JavaBean encapsulating data about temperature in a given city
 */
public class WeatherInfo {
    private final String cityName;
    private final int temperature;
    private final long created;

    public WeatherInfo(String cityName, int temperature, long created) {
        this.cityName = cityName;
        this.temperature = temperature;
        this.created = created;
    }

    public String getCityName() {
        return cityName;
    }

    public int getTemperature() {
        return temperature;
    }

    public long getCreated() {
        return created;
    }
}
