package com.example.boveda.controllers;

import com.example.boveda.services.CardResponsePost;
import com.example.boveda.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cardvault")
public class CardController {

    @Autowired
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    //Endpoints
    @PostMapping(path = "/token", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CardResponsePost> generateToken (@RequestBody String creditCardNumber){
        return cardService.generateToken(creditCardNumber);
    }

    @GetMapping("/number/{token}")
    public ResponseEntity<String> getCreditCardNumber (@PathVariable String token){
        return cardService.getCreditCardNumber(token);
    }
}
