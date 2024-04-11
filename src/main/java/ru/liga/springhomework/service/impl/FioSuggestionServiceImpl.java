package ru.liga.springhomework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.liga.springhomework.exception.FioSuggestionException;
import ru.liga.springhomework.model.FioSuggestion;
import ru.liga.springhomework.model.FioSuggestionRequest;
import ru.liga.springhomework.model.FioSuggestionResponse;
import ru.liga.springhomework.service.FioSuggestionService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FioSuggestionServiceImpl implements FioSuggestionService {

    private final RestTemplate daDataRestTemplate;

    @Value("${dadata.api.base-url}")
    private String baseUrl;

    public FioSuggestionResponse getSuggestions(String query, Integer count) {

        String url = baseUrl + "/fio";
        FioSuggestionRequest request = new FioSuggestionRequest(query, count);

        try {
            ResponseEntity<FioSuggestionResponse> responseEntity = daDataRestTemplate.postForEntity(url, request, FioSuggestionResponse.class);
            HttpStatusCode statusCode = responseEntity.getStatusCode();

            if (statusCode != HttpStatus.OK) {
                throw new FioSuggestionException("No response from DaData or bad status");
            }

            List<FioSuggestion> suggestions = Optional.ofNullable(responseEntity.getBody())
                    .map(FioSuggestionResponse::suggestions)
                    .orElseGet(List::of)
                    .stream()
                    .map(suggestion -> new FioSuggestion(suggestion.fullName(), suggestion.data()))
                    .toList();

            return new FioSuggestionResponse(suggestions);
        } catch (Exception e) {
            throw new FioSuggestionException("Error during the DaData API request", e);
        }
    }
}
