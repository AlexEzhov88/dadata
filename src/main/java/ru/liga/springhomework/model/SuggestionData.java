package ru.liga.springhomework.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SuggestionData(
        @JsonProperty("country") String country,
        @JsonProperty("city_with_type") String city,
        @JsonProperty("postal_code") String postalCode
) {
}