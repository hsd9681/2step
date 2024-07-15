package com.example.demo.domain.card.service;


import com.example.demo.common.exception.CustomException;
import com.example.demo.common.exception.ErrorCode;
import com.example.demo.domain.board.BoardService;
import com.example.demo.domain.card.dto.CardRequestDto;
import com.example.demo.domain.card.dto.CardResponseDto;
import com.example.demo.domain.card.entity.Card;
import com.example.demo.domain.card.repository.CardRepository;
import com.example.demo.domain.column.entity.BoardColumn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final BoardService boardService;

    public List<CardResponseDto> getCards() {
        List<CardResponseDto> result = new ArrayList<>();
        List<String> statuses = List.of("todo", "in-progress", "done","emergency");  // 4종류 status 값을 넣어주세요

        for (String status : statuses) {
            List<Card> cards = cardRepository.findByStatus(status);
            result.addAll(cards.stream()
                    .map(CardResponseDto::new)
                    .toList());
        }

        return result;
    }

    public CardResponseDto createCard(CardRequestDto requestDto, String username, BoardColumn boardColumn) {
        Card card =cardRepository.save(new Card(requestDto ,username ,boardColumn));
        return new CardResponseDto(card);
    }

    public CardResponseDto updateCard(Long cardId, CardRequestDto requestDto, String username) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CustomException(ErrorCode.CARD_NOT_FOUND));
        BoardColumn boardColumn = new BoardColumn();
        card.update(requestDto, username, boardColumn);
        cardRepository.save(card);
        return new CardResponseDto(card);
    }

    public void deleteCard(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CustomException(ErrorCode.CARD_NOT_FOUND));
        cardRepository.delete(card);
    }

    public Card findById(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(() -> new CustomException(ErrorCode.CARD_NOT_FOUND));
    }


}
