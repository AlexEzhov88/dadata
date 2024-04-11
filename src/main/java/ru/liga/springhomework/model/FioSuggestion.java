package ru.liga.springhomework.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FioSuggestion(@JsonProperty("value") String fullName,
                            @JsonProperty("data") FioData data) {
}