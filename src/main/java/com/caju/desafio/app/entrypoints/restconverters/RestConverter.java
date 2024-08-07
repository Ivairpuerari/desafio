package com.caju.desafio.app.entrypoints.restconverters;

public interface RestConverter<E, T> {
    public T map(E arg);
}
