package com.example.boveda.controllers;


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
    @Autowired
    private CardService cardService;

    @PostMapping("/card")
    public ResponseEntity<String> getToken (@RequestBody String CardNumber) throws Exception {
        try {
            String token = cardService.encryptNumber(CardNumber);
            return new ResponseEntity<>(token, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.severe("Error: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/card/{token}")
    public ResponseEntity<String> getCardNumber(@PathVariable String token) {
        try {
            return new ResponseEntity<>(cardService.getNumber(token), HttpStatus.OK);
        } catch (ChangeSetPersister.NotFoundException e) {
            LOGGER.severe("Error: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOGGER.severe("Error: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
