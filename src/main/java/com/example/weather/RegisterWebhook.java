package com.example.weather;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Registers URL of our telegram bot taking Telegram token and webhook URL as params
 */
public class RegisterWebhook {
    public static void main(String[] args) {
        String token = args[0];
        String webHookUrl = args[1];

        String telegramUrl = "https://api.telegram.org/bot" + token + "/setWebhook";
        String payload = "{\"url\":\"" + webHookUrl + "\"}";

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(payload, headers);

        ResponseEntity<String> responseEntityStr = rest.postForEntity(telegramUrl, request, String.class);

        System.out.println("registered bot webhook: " + webHookUrl);
    }
}
