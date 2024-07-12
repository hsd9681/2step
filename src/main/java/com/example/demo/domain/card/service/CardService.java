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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;

    public List<CardResponseDto> getCards() {
        List<CardResponseDto> result = new ArrayList<>();
        List<String> statuses = List.of("todo", "in-progress", "done","emergency");  // 4종류 status 값을 넣어주세요

        for (String status : statuses) {
            List<Card> cards = cardRepository.findByStatus(status);
            result.addAll(cards.stream()
                    .map(CardResponseDto::new)
                    .collect(Collectors.toList()));
        }

        return result;
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