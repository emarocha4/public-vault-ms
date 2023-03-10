package com.example.boveda.controllers;


import com.example.boveda.models.Card;
import com.example.boveda.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.logging.Logger;

@RestController
@RequestMapping("/pci-vault")
public class CardController {
    
    private static final Logger LOGGER = Logger.getLogger(CardController.class.getName());
    private static final String KEY_VAULT = System.getenv("KEY-VAULT");
    @Autowired
    private CardService cardService;


    @PostMapping("/card")
    public ResponseEntity<String> createCard(@RequestBody Card cardRequest) {
        try {
            String encryptedCardNumber = cardService.encryptCardNumber(cardRequest.getCardNumber());
            String token = cardService.generateToken();
            cardService.saveCard(encryptedCardNumber, token, KEY_VAULT);
            return ResponseEntity.ok(token);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            LOGGER.severe("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/card/{token}")
    public ResponseEntity<String> getCardNumber(@PathVariable String token) {
        try {
            String cardNumber = cardService.getCardNumberByToken(token);
            return ResponseEntity.ok(cardNumber);
        } catch (ChangeSetPersister.NotFoundException e) {
            LOGGER.severe("Error: " + e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            LOGGER.severe("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
