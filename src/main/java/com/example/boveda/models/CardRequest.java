package com.example.boveda.models;


public class CardRequest {

    private String cardNumber;

    public CardRequest() {
    }

    public CardRequest(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

}
