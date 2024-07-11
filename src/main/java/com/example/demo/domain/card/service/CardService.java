package com.example.demo.domain.card.service;


import com.example.demo.domain.card.dto.CardRequestDto;
import com.example.demo.domain.card.dto.CardResponseDto;
import com.example.demo.domain.card.entity.Card;
import com.example.demo.domain.card.repository.CardRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;

    public List<CardResponseDto> getCards(){
        List<Card> cards = cardRepository.findAll();
        List<CardResponseDto> cardResponseDtos = new ArrayList<>();
        for (Card card : cards) {
            cardResponseDtos.add(new CardResponseDto(card));
        }
        return cardResponseDtos;
    }

    public  CardResponseDto createCard(CardRequestDto requestDto) {
        Card card =cardRepository.save(new Card(requestDto));
        return new CardResponseDto(card);
    }

    public CardResponseDto updateCard(Long cardId, @Valid CardRequestDto requestDto) {
        return null;
    }


    public void deleteCard(Long cardId) {
    }
}