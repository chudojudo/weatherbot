package com.example.weather.process;

import com.example.weather.data.WeatherInfoService;
import com.example.weather.data.ApiErrorException;
import com.example.weather.data.NotFoundException;
import com.example.weather.telegram.TelegramApi;
import com.example.weather.telegram.Update;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * Service which actually processes/schedules for processing updates received from Telegram.
 * This particular implementation uses {@link Executors#newFixedThreadPool(int)} for an async processing.
 */
@Service("updateProcessor")
public class UpdateProcessor implements Consumer<Update> {
    private static final Executor executor = Executors.newFixedThreadPool(2);

    private final WeatherInfoService weatherInfoService;
    private final TelegramApi telegramApi;

    public UpdateProcessor(WeatherInfoService weatherInfoService, TelegramApi telegramApi) {
        this.weatherInfoService = weatherInfoService;
        this.telegramApi = telegramApi;
    }

    @Override
    public void accept(Update update) {
        executor.execute(() -> {
            String city = update.getMessage().getText();
            try {
                int temperature = weatherInfoService.getWeatherInfo(city).getTemperature();

                telegramApi.sendMessage("It's " + temperature + " degrees Celsius now in " + city,
                        update.getMessage().getChat().getId());
            }
            catch (NotFoundException e) {
                telegramApi.sendMessage("Can't recognize city '" + city + "'",
                        update.getMessage().getChat().getId());
            }
            catch (ApiErrorException e) {
                telegramApi.sendMessage("Weather API not available, please try later",
                        update.getMessage().getChat().getId());
            }
        });
    }
}
