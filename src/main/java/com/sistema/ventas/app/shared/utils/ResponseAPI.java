package com.sistema.ventas.app.shared.utils;

public class ResponseAPI<T> {
    private final Integer statusCode;
    private final String message;
    private final T data;

    public ResponseAPI(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = null;
    }

    public ResponseAPI(Integer statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
