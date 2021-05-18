package com.example.weather.data.openweather;

/**
 * JavaBean storing response of a OpenWeather API service
 */
class OpenWeatherResponse {
    static class Main {
        double temp;

        public double getTemp() {
            return temp;
        }
    }

    Main main;

    public Main getMain() {
        return main;
    }

    public double getTemp() {
        return getMain() == null ? 0 : getMain().getTemp();
    }

    @Override
    public String toString() {
        return String.valueOf(main.temp);
    }
}
