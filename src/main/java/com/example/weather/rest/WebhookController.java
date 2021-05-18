package com.example.weather.rest;

import com.example.weather.telegram.Update;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.function.Consumer;

import org.slf4j.Logger;

/**
 * Spring Rest controller receiving Updates from Telegram once Webhook is registered
 */
@RestController
@RequestMapping(path="${bot.path}", consumes = "application/json")
public class WebhookController {
    private static final Logger log = LoggerFactory.getLogger(WebhookController.class);

    @Resource(name="updateProcessor")
    private final Consumer<Update> updateConsumer;

    public WebhookController(Consumer<Update> updateConsumer) {
        this.updateConsumer = updateConsumer;
    }

    @PostMapping()
    public void processUpdate(@RequestBody Update update) {
        log.trace("received update from telegram: {}", update);
        updateConsumer.accept(update);
    }
}
