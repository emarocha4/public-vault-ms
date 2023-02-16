package com.example.boveda.services;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CardResponsePost {

    @JsonProperty("Token")
    private final String token;

    @JsonProperty("Id")
    private final int id;

    public CardResponsePost(String token, int id){
        this.token = token;
        this.id = id;
    }
}
