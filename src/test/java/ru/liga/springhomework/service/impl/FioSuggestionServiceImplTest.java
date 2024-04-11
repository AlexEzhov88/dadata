package ru.liga.springhomework.service.impl;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.liga.springhomework.BaseTestClass;
import ru.liga.springhomework.model.FioData;
import ru.liga.springhomework.model.FioSuggestion;
import ru.liga.springhomework.model.FioSuggestionRequest;
import ru.liga.springhomework.model.FioSuggestionResponse;
import ru.liga.springhomework.model.Gender;
import ru.liga.springhomework.service.FioSuggestionService;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

class FioSuggestionServiceImplTest extends BaseTestClass {

    @Autowired
    private FioSuggestionService fioSuggestionService;

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
    @DisplayName("Получение подсказок по ФИО")
    void getSuggestions() throws Exception {
        FioSuggestionResponse expectedResponse = new FioSuggestionResponse(java.util.List.of(new FioSuggestion("Иван", new FioData(Gender.MALE))));

        setupMockFioSuggestionResponse(expectedResponse);

        FioSuggestionRequest request = new FioSuggestionRequest("Иван", 1);
        FioSuggestionResponse response = fioSuggestionService.getSuggestions(request.query(), request.count());

        assertThat(response.suggestions()).isNotEmpty().extracting("fullName", "data.gender")
                .containsExactlyInAnyOrder(tuple("Иван", Gender.MALE));
    }

    private void setupMockFioSuggestionResponse(FioSuggestionResponse response) throws Exception {
        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/api/v2/suggest/fio"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(response))));
    }
}
