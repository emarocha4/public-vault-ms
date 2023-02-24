package com.example.boveda.services;

import com.example.boveda.models.Card;
import com.example.boveda.repositories.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {CardService.class})
public class CardServiceTest {

    @MockBean
    private CardRepository cardRepository;

    @Autowired
    private CardService cardService;

    @BeforeEach
    public void setUp() {
        System.setProperty("KEY-VAULT", "7e23546f75a9562a3a294a595c7d62b9");
    }

    @ParameterizedTest
    @CsvSource({
            "1234567890123456, false, false",
            ", true, false",
            "1234abcd5678efgh, false, true"
    })
    public void testEncryptCardNumber(String cardNumber, boolean isEmpty, boolean isNonNumeric) throws Exception {
        if (isEmpty || isNonNumeric) {
            assertThrows(IllegalArgumentException.class, () -> cardService.encryptCardNumber(cardNumber));
            return;
        }
        String encryptedCardNumber = cardService.encryptCardNumber(cardNumber);
        assertNotNull(encryptedCardNumber);
        assertNotEquals(cardNumber, encryptedCardNumber);
    }

    @Test
    public void saveCard_shouldCallSaveMethod() {
        String encryptedCardNumber = "encrypted-card-number";
        String token = "token";
        String key = "key";

        cardService.saveCard(encryptedCardNumber, token, key);

        verify(cardRepository, times(1)).save(any(Card.class));
    }

    @ParameterizedTest
    @CsvSource({
            "6IY045WuNrnwh7yCW8kdIWmZNH7e++UtuVDFSdkQF7s=, 7e23546f75a9562a3a294a595c7d62b9, 1234567890123456",
            "invalid_base64_value,,",
            ",,",
    })
    public void testDecryptCardNumber(String encodedCardNumber, String key, String expectedDecryptedCardNumber) throws Exception {
        if (expectedDecryptedCardNumber != null) {
            String decryptedCardNumber = cardService.decryptCardNumber(encodedCardNumber, key);
            assertEquals(expectedDecryptedCardNumber, decryptedCardNumber);
        } else {
            assertThrows(Exception.class, () -> cardService.decryptCardNumber(encodedCardNumber, key));
        }
    }

    @ParameterizedTest
    @CsvSource({
            "token,6IY045WuNrnwh7yCW8kdIWmZNH7e++UtuVDFSdkQF7s=, 7e23546f75a9562a3a294a595c7d62b9" +
                    "", // Cuando el token es valido y se encuentra en la base de datos
            "invalidToken_not_found,,", // Cuando el token es invalido y no se encuentra en la base de datos
    })
    public void testGetCardNumberByToken(String token, String cardNumber, String key) throws Exception {
        try {
            if ("token".equals(token)) { // Cuando el token es valido vamos a hacer que la base de datos nos devuelva un objeto card
                Card card = new Card(cardNumber, token, key);
                when(cardRepository.findByToken(token)).thenReturn(card);
            } else {
                when(cardRepository.findByToken(token)).thenReturn(null);
            }
            String result = cardService.getCardNumberByToken(token);
            assertEquals("1234567890123456", result);
        } catch (ChangeSetPersister.NotFoundException e) {
            if ("invalidToken_not_found".equals(token)) {
                assertTrue(true);
            } else {
                fail();
            }
        }
    }
}