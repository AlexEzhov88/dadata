package ru.liga.springhomework.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Suggestion(@JsonProperty("value") String fullAddress,
                         @JsonProperty("data") SuggestionData data
) {
    public static Suggestion of(String fullAddress, String country, String city, String postalCode) {
        SuggestionData data = new SuggestionData(country, city, postalCode);
        return new Suggestion(fullAddress, data);
    }
}
