package com.example.weather.telegram;

/**
 * API Interface encapsulating synchronous communication with Telegram API.
 * Communication errors will be thrown as {@link RuntimeException}
 */
public interface TelegramApi {
    /**
     * Sends given text message to the given chat
     * @param text message to send
     * @param chatId ID of destination chat
     */
    void sendMessage(String text, String chatId);
}
