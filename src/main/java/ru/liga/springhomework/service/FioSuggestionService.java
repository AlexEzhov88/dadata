package ru.liga.springhomework.service;

import ru.liga.springhomework.model.FioSuggestionResponse;

public interface FioSuggestionService {
    FioSuggestionResponse getSuggestions(String query, Integer count);
}
