package com.example.weather.data;

public class ApiErrorException extends Exception {
    public ApiErrorException(Throwable cause) {
        super(cause);
    }
}
