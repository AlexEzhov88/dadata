package ru.liga.springhomework.exception;

public class AddressSuggestionException extends RuntimeException {

    public AddressSuggestionException(String message) {super(message);}

    public AddressSuggestionException(String message, Throwable cause) {
        super(message, cause);
    }
}
