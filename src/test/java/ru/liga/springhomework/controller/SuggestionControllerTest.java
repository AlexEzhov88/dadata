package ru.liga.springhomework.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import ru.liga.springhomework.BaseTestClass;
import ru.liga.springhomework.model.AddressSuggestionResponse;
import ru.liga.springhomework.model.FioData;
import ru.liga.springhomework.model.FioSuggestion;
import ru.liga.springhomework.model.FioSuggestionResponse;
import ru.liga.springhomework.model.Gender;
import ru.liga.springhomework.model.Suggestion;
import ru.liga.springhomework.model.SuggestionData;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SuggestionControllerTest extends BaseTestClass {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @BeforeAll
    static void beforeAll() {
        BaseTestClass.setupMockServer();
    }

    @AfterAll
    static void afterAll() {
        BaseTestClass.tearDownMockServer();
    }

    @Test
    @DisplayName("Получение подсказок по адресу")
    void getAddressSuggestionsShouldReturnSuggestions() throws JsonProcessingException {
        String addressUrl = "http://localhost:" + port + "/api/v2/suggest/address-suggestions";
        setupMockAddressSuggestionResponse();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> addressRequest = new HttpEntity<>("{\"query\":\"Москва\",\"count\":1}", headers);

        ResponseEntity<String> addressResponse = testRestTemplate.exchange(addressUrl, HttpMethod.POST, addressRequest, String.class);

        assertThat(addressResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(addressResponse.getBody())
                .contains("\"value\":\"г Москва, ул Сухонская, д 11\"")
                .contains("\"country\":\"Россия\"")
                .contains("\"city_with_type\":\"Москва\"")
                .contains("\"postal_code\":\"127642\"");
    }

    @Test
    @DisplayName("Получение подсказок по ФИО")
    void getFioSuggestionsShouldReturnSuggestions() throws JsonProcessingException {
        String fioUrl = "http://localhost:" + port + "/api/v2/suggest/fio-suggestions";
        setupMockFioSuggestionResponse();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> fioRequest = new HttpEntity<>("{\"query\":\"Иван\",\"count\":1}", headers);

        ResponseEntity<String> fioResponse = testRestTemplate.exchange(fioUrl, HttpMethod.POST, fioRequest, String.class);

        assertThat(fioResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(fioResponse.getBody()).contains("\"value\":\"Иван\"");
        assertThat(fioResponse.getBody()).contains("\"gender\":\"MALE\"");
    }

    private void setupMockAddressSuggestionResponse() throws JsonProcessingException {
        AddressSuggestionResponse mockResponse = new AddressSuggestionResponse(List.of(
                new Suggestion("г Москва, ул Сухонская, д 11", new SuggestionData("Россия", "Москва", "127642"))
        ));

        wireMockServer.stubFor(WireMock.post(WireMock.urlEqualTo("/api/v2/suggest/address"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(mockResponse))));
    }

    private void setupMockFioSuggestionResponse() throws JsonProcessingException {
        FioSuggestionResponse mockResponse = new FioSuggestionResponse(List.of(
                new FioSuggestion("Иван", new FioData(Gender.MALE))
        ));

        wireMockServer.stubFor(WireMock.post(WireMock.urlEqualTo("/api/v2/suggest/fio"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(mockResponse))));
    }
}
