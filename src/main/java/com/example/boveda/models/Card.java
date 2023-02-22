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
    @Column(name = "encrypted-card-number")
    private String cardNumber;
    @Column(name = "token")
    private String token;
    @Column(name = "creation-date")
    private Date creationDate;


    public Card() {
    }

    public Card(String cardNumber, String token) {
        this.cardNumber = cardNumber;
        this.token = token;
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
}

