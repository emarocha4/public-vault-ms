package com.example.boveda.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "encrypted_card_number", nullable = false)
    private String cardNumber;
    @Column(name = "token", nullable = false)
    private String token;
    @Column(name = "environment_variable_key", nullable = false)
    private String key;
    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    public Card() {
    }

    public Card(String cardNumber, String token, String key) {
        this.cardNumber = cardNumber;
        this.token = token;
        this.key = key;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String encryptedNumber) {
        this.cardNumber = encryptedNumber;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

