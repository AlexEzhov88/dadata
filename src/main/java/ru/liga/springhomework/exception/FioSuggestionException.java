package ru.liga.springhomework.exception;

public class FioSuggestionException extends RuntimeException {

    public FioSuggestionException(String message) {super(message);}

    public FioSuggestionException(String message, Throwable cause) {
        super(message, cause);
    }
}
