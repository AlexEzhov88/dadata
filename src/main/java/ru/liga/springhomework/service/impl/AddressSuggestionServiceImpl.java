package ru.liga.springhomework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.liga.springhomework.exception.AddressSuggestionException;
import ru.liga.springhomework.model.AddressSuggestionRequest;
import ru.liga.springhomework.model.AddressSuggestionResponse;
import ru.liga.springhomework.model.Suggestion;
import ru.liga.springhomework.service.AddressSuggestionService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressSuggestionServiceImpl implements AddressSuggestionService {

    private final RestTemplate daDataRestTemplate;

    @Value("${dadata.api.base-url}")
    private String baseUrl;

    @Override
    public AddressSuggestionResponse getSuggestions(String query, Integer count) {

        String url = baseUrl + "/address";
        AddressSuggestionRequest request = new AddressSuggestionRequest(query, count);

        try {
            ResponseEntity<AddressSuggestionResponse> responseEntity = daDataRestTemplate.postForEntity(url, request, AddressSuggestionResponse.class);

            if (responseEntity.getStatusCode() != HttpStatus.OK) {
                throw new AddressSuggestionException("No response from DaData or bad status");
            }

            List<Suggestion> suggestions = Optional.ofNullable(responseEntity.getBody())
                    .map(AddressSuggestionResponse::suggestions)
                    .orElseGet(List::of)
                    .stream()
                    .map(suggestion -> Suggestion.of(
                            suggestion.fullAddress(),
                            suggestion.data().country(),
                            suggestion.data().city(),
                            suggestion.data().postalCode()))
                    .toList();

            return new AddressSuggestionResponse(suggestions);
        } catch (Exception e) {
            throw new AddressSuggestionException("Failed to parse response from DaData", e);
        }
    }
}
