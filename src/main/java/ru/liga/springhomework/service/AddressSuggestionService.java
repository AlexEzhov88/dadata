package ru.liga.springhomework.service;

import ru.liga.springhomework.model.AddressSuggestionResponse;

public interface AddressSuggestionService {
    AddressSuggestionResponse getSuggestions(String query, Integer count);
}
