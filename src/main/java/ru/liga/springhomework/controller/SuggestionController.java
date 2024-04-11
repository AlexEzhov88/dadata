package ru.liga.springhomework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.springhomework.model.AddressSuggestionRequest;
import ru.liga.springhomework.model.AddressSuggestionResponse;
import ru.liga.springhomework.model.FioSuggestionRequest;
import ru.liga.springhomework.model.FioSuggestionResponse;
import ru.liga.springhomework.service.AddressSuggestionService;
import ru.liga.springhomework.service.FioSuggestionService;

@RestController
@RequestMapping("/api/v2/suggest")
@RequiredArgsConstructor
public class SuggestionController {

    private final AddressSuggestionService addressSuggestionService;
    private final FioSuggestionService fioSuggestionService;

    @PostMapping("/address-suggestions")
    @Operation(summary = "Get address suggestions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = AddressSuggestionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<AddressSuggestionResponse> getAddressSuggestions(@RequestBody AddressSuggestionRequest suggestionRequest) {
        AddressSuggestionResponse response = addressSuggestionService.getSuggestions(suggestionRequest.query(), suggestionRequest.count());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/fio-suggestions")
    @Operation(summary = "Get FIO suggestions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = FioSuggestionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<FioSuggestionResponse> getFioSuggestions(@RequestBody FioSuggestionRequest request) {
        FioSuggestionResponse response = fioSuggestionService.getSuggestions(request.query(), request.count());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
