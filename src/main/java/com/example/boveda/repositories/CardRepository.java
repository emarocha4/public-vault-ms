package com.example.boveda.repositories;


import com.example.boveda.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
    Card findByToken(String token);
}