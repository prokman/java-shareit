package ru.practicum.shareit.exceptions;

public class DuplicateDataException extends RuntimeException {
    public DuplicateDataException(String message) {
        super(message);
    }
}
