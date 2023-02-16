package com.example.boveda.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class CardService {

    private static final Map<String, String> tokens = new HashMap<>();

    public static ResponseEntity<CardResponsePost> generateToken(String creditCardNumber) {
        String token = UUID.randomUUID().toString();
        tokens.put(token, creditCardNumber);
        return new ResponseEntity<>(new CardResponsePost(token, 222), HttpStatus.OK);
    }

    public static ResponseEntity<String> getCreditCardNumber(String token) {
        if (tokens.containsKey(token)) {
            return new ResponseEntity<>(tokens.get(token), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Token inválido", HttpStatus.BAD_REQUEST);
        }
    }

}

