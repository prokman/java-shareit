package ru.practicum.shareit.exceptions;

import lombok.Getter;

@Getter
public class ErrorResponse {
    String error;
    String description;

    public ErrorResponse(String error, String description) {
        this.error = error;
        this.description = description;
    }
}
