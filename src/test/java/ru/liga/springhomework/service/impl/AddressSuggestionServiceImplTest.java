package ru.liga.springhomework.service.impl;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.liga.springhomework.BaseTestClass;
import ru.liga.springhomework.model.AddressSuggestionResponse;
import ru.liga.springhomework.model.Suggestion;
import ru.liga.springhomework.service.AddressSuggestionService;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.assertj.core.api.Assertions.assertThat;

class AddressSuggestionServiceImplTest extends BaseTestClass {

    @Autowired
    private AddressSuggestionService addressSuggestionService;

    @BeforeAll
    static void beforeAll() {
        BaseTestClass.setupMockServer();
    }

    @AfterAll
    static void afterAll() {
        BaseTestClass.tearDownMockServer();
    }

    @BeforeEach
    void setUp() {
        WireMock.configureFor("localhost", wireMockServer.port());
    }

    @Test
    @DisplayName("Получение подсказок по адресу")
    void getSuggestions() throws Exception {
        AddressSuggestionResponse mockResponse = new AddressSuggestionResponse(List.of(
                Suggestion.of("г Москва, ул Сухонская, д 11", "Россия", "Москва", "127642")
        ));

        setupMockResponse(mockResponse);

        AddressSuggestionResponse response = addressSuggestionService.getSuggestions("Москва", 1);

        assertThat(response.suggestions()).isNotEmpty();
        assertThat(response.suggestions().get(0).fullAddress()).isEqualTo("г Москва, ул Сухонская, д 11");
        assertThat(response.suggestions().get(0).data().country()).isEqualTo("Россия");
        assertThat(response.suggestions().get(0).data().city()).isEqualTo("Москва");
        assertThat(response.suggestions().get(0).data().postalCode()).isEqualTo("127642");
    }

    private void setupMockResponse(Object response) throws Exception {
        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/api/v2/suggest/address"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(response))));
    }
}
