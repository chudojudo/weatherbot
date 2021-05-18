package com.example.weather.telegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Actual implementation of the {@link TelegramApi} interface.
 * Can be used from multiple threads once {@link #setApikey(String)} called.
 */
@Component
@ConfigurationProperties(prefix = "telegram")
public class TelegramApiImpl implements TelegramApi {
    private static final Logger log = LoggerFactory.getLogger(TelegramApiImpl.class);
    private static final String url = "https://api.telegram.org/bot{apikey}/sendMessage";

    private String apikey;

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    @Override
    public void sendMessage(String text, String chatId) {
        log.trace("Sending '{}' to chat {}", text, chatId);

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Message payload = new Message(chatId, text);
        HttpEntity<Message> request = new HttpEntity<>(payload, headers);

        ResponseEntity<String> responseEntityStr = rest.postForEntity(url, request, String.class, apikey);
        log.trace("received {}", responseEntityStr);
    }
}
