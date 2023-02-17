package com.example.boveda.controllers;


import com.example.boveda.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private CardService cardService;

    @PostMapping("/vault-cards")
    public ResponseEntity<String> createCard(@RequestBody String CardNumber) throws Exception {
        try {
            String token = cardService.encryptNumber(CardNumber);
            return new ResponseEntity<>(token, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/vault-cards/{token}")
    public ResponseEntity<String> getCardNumber(@PathVariable String token) {
        try {
            String number = cardService.getNumber(token);
            return new ResponseEntity<>(number, HttpStatus.OK);
        } catch (ChangeSetPersister.NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
