package com.example.boveda.services;

import com.example.boveda.models.Card;
import com.example.boveda.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Service
public class CardService {

    private static final String KEY = System.getenv("KEY-VAULT");
    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final String typeOfAlgorithm = "AES";


    @Autowired
    private final CardRepository cardRepository;
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }


    public String encryptCardNumber(String cardNumber) throws Exception {
        if (cardNumber == null || cardNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("The input string cannot be empty.");
        }
        if (!cardNumber.matches("^[0-9]+$")) {
            throw new IllegalArgumentException("The input string must contain only numbers.");
        }
        SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), typeOfAlgorithm);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedNumber = cipher.doFinal(cardNumber.getBytes());
        return Base64.getEncoder().encodeToString(encryptedNumber);
    }

    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    public void saveCard(String encryptedCardNumber, String token, String key) {
        Card card = new Card();
        card.setCardNumber(encryptedCardNumber);
        card.setToken(token);
        card.setKey(key);
        card.setCreationDate(new Date());
        cardRepository.save(card);
    }

    public String decryptCardNumber(String encodedCardNumber, String key) throws Exception {
        byte[] cardNumberBytes = Base64.getDecoder().decode(encodedCardNumber);
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), typeOfAlgorithm);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedNumber = cipher.doFinal(cardNumberBytes);
        return new String(decryptedNumber);
    }


    public String getCardNumberByToken(String token) throws Exception {
        Card card = cardRepository.findByToken(token);
        if (card == null) {
            throw new ChangeSetPersister.NotFoundException();
        }
        String key = card.getKey();
        String encryptedCardNumber = card.getCardNumber();
        return decryptCardNumber(encryptedCardNumber, key);
    }
}