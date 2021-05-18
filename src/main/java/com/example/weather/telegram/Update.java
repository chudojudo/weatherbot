package com.example.weather.telegram;

/**
 * Java Bean representing a simplified Update received from Telegram Webhook (in a simplified form)
 *
 * See https://core.telegram.org/bots/api#getting-updates
 */
public class Update {
    public static class Chat {
        String id;

        public String getId() {
            return id;
        }
    }

    public static class Message {
        String text;
        Chat chat;

        public String getText() {
            return text;
        }

        public Chat getChat() {
            return chat;
        }
    }

    private int updateId;
    private Message message;

    public int getUpdateId() {
        return updateId;
    }

    public void setUpdateId(int updateId) {
        this.updateId = updateId;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.getMessage().getText();
    }
}
