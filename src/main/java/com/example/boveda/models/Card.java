package com.example.boveda.models;

import jakarta.persistence.*;

@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "encrypted-card-number")
    private String cardNumber;

    @Column(name = "token")
    private String token;

    public Card() {
    }

    public Card(String token, byte[] cardNumber) {
    }

    public Card(int id, String cardNumber, String token) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.token = token;
    }

    // Getters & Setters
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
}

