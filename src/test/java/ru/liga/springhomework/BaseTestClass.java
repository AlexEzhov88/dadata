package ru.liga.springhomework;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class BaseTestClass {
    @Autowired
    protected ObjectMapper objectMapper;

    protected static WireMockServer wireMockServer;

    protected static void setupMockServer() {
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
    }

    protected static void tearDownMockServer() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }
}
