package com.caju.desafio.domain.enums;

public enum StatusCodeApplicationEnum {
    SUCCESS("00"),
    REJECT("51"),
    ERROR("07");

    public final String value;

    StatusCodeApplicationEnum(String value) {
        this.value = value;
    }
}
