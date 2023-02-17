package com.example.boveda.services;

import com.example.boveda.models.Card;
import com.example.boveda.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.UUID;

@Service
public class CardService {

    private static final String KEY = "public-vault-key-test-cba-2023";
    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";

    @Autowired
    private CardRepository cardRepository;

    public String encryptNumber(String number) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedNumber = cipher.doFinal(number.getBytes());
        String token = UUID.randomUUID().toString();
        Card card = new Card(token, encryptedNumber);
        cardRepository.save(card);
        return token;
    }
    public String getNumber(String token) throws Exception {
        Card card = cardRepository.findByToken(token);
        if (card == null) {
            throw new ChangeSetPersister.NotFoundException();
        }
        SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedNumber = cipher.doFinal(card.getCardNumber().getBytes());
        return new String(decryptedNumber);
    }

}

