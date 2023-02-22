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
    private CardRepository cardRepository;

    public String encryptNumber(String number) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), typeOfAlgorithm);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedNumber = cipher.doFinal(number.getBytes());
        String cardNumber = Base64.getEncoder().encodeToString(encryptedNumber); // Codificación en Base64
        String token = UUID.randomUUID().toString();
        Card card = new Card(cardNumber, token);
        card.setCreationDate(new Date()); // Establecer la fecha actual como de creacion
        cardRepository.save(card);
        return token;
    }
    public String getNumber(String token) throws Exception {
        Card card = cardRepository.findByToken(token);
        if (card == null) {
            throw new ChangeSetPersister.NotFoundException();
        }
        SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), typeOfAlgorithm);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] cardNumberBytes = Base64.getDecoder().decode(card.getCardNumber()); // Decodificación de Base64
        byte[] decryptedNumber = cipher.doFinal((cardNumberBytes));
        return new String(decryptedNumber);
    }

}

