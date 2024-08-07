package com.caju.desafio.domain.exceptions;

public class DataAccessException extends RuntimeException {
    public DataAccessException(String message, Throwable err) {
        super(message, err);
    }
}
