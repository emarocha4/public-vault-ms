package com.example.boveda.controllers;

import com.example.boveda.models.Card;
import com.example.boveda.services.CardService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CardControllerTest {

    @MockBean
    private CardService cardService;

    @Autowired
    private CardController cardController;

    @ParameterizedTest
    @CsvSource({"1234567890123456, token, 7e23546f75a9562a3a294a595c7d62b9, valid",
            "ABCDEFGHIJKLMNOP,,,invalid",
            ",,, invalid"
    })
    public void testCreateCard(String cardNumber, String token, String key, String expectedValidation) throws Exception {
        Card card = new Card(cardNumber, token, key);
        if ("invalid".equals(expectedValidation)) {
            String errorMessage = "The input string must contain only numbers.";
            when(cardService.encryptCardNumber(cardNumber)).thenThrow(new IllegalArgumentException(errorMessage));

            ResponseEntity<String> response = cardController.createCard(card);

            assertAll("Invalid card",
                    () -> assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()),
                    () -> assertNotNull(response.getBody()),
                    () -> assertEquals(errorMessage, response.getBody())
            );
        } else if ("valid".equals(expectedValidation)) {
            when(cardService.encryptCardNumber(cardNumber)).thenReturn("encrypted");
            when(cardService.generateToken()).thenReturn(token);

            ResponseEntity<String> response = cardController.createCard(card);

            assertAll("Valid card",
                    () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                    () -> assertNotNull(response.getBody()),
                    () -> assertEquals(token, response.getBody())
            );
        }
    }

    @ParameterizedTest
    @CsvSource({
            "token,1234567890123456,OK",
            "non-existent-token,,NOT_FOUND",
            "internal-server-error-token,,INTERNAL_SERVER_ERROR"
    })
    public void testGetCardNumber(String token, String expectedCardNumber, HttpStatus expectedHttpStatus) throws Exception {
        if (!expectedHttpStatus.equals(HttpStatus.OK)) {
            when(cardService.getCardNumberByToken(token)).thenThrow(expectedHttpStatus.equals(HttpStatus.NOT_FOUND) ?
                    new ChangeSetPersister.NotFoundException() : new RuntimeException("Internal Server Error"));
        } else {
            when(cardService.getCardNumberByToken(token)).thenReturn(expectedCardNumber);
        }

        ResponseEntity<String> response = cardController.getCardNumber(token);

        assertAll("response",
                () -> assertEquals(expectedHttpStatus, response.getStatusCode()),
                () -> assertEquals(expectedCardNumber, response.getBody())
        );
    }
}
