package ru.yandex.practicum.filmorate.Exception;

public class ErrorException extends RuntimeException {
    public ErrorException(final String message) {
        super(message);
    }
}
