package com.example.demo.domain.card.service;


import com.example.demo.common.exception.CustomException;
import com.example.demo.common.exception.ErrorCode;
import com.example.demo.domain.card.dto.CardRequestDto;
import com.example.demo.domain.card.dto.CardResponseDto;
import com.example.demo.domain.card.entity.Card;
import com.example.demo.domain.card.repository.CardRepository;
import com.example.demo.domain.column.entity.BoardColumn;
import com.example.demo.domain.permission.entity.Permission;
import com.example.demo.domain.permission.entity.PermissionType;
import com.example.demo.domain.permission.repository.PermissionRepository;
import com.example.demo.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final PermissionRepository permissionRepository;

    public List<CardResponseDto> getCards() {
        List<CardResponseDto> result = new ArrayList<>();
        List<String> statuses = List.of("todo", "in-progress", "done", "emergency");  // 4종류 status 값을 넣어주세요

        for (String status : statuses) {
            List<Card> cards = cardRepository.findByStatus(status);
            result.addAll(cards.stream()
                    .map(CardResponseDto::new)
                    .toList());
        }

        return result;
    }

    public CardResponseDto createCard(CardRequestDto requestDto, String username, BoardColumn boardColumn, User user) {
        Card card = cardRepository.save(new Card(requestDto, username, boardColumn, user));
        return new CardResponseDto(card);
    }

    public CardResponseDto updateCard(Long cardId, CardRequestDto requestDto, String username, User user) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CustomException(ErrorCode.CARD_NOT_FOUND));
        BoardColumn boardColumn = new BoardColumn();
        card.update(requestDto, username, boardColumn, user);
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

    public boolean hasPermissionForCard(Long userId, Long cardId) {
        // 카드 조회 
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CustomException(ErrorCode.CARD_NOT_FOUND));
        // 보드 ID 추출
        Long boardId = card.getBoardColumn().getBoard().getId();
        // 사용자 권한 조회
        Permission permission = permissionRepository.findByUser_IdAndBoard_Id(userId, boardId);
       // 권한 검사 및 결과를 반환 (권한이 매니저이거나 카드를 생성한 사용자의 ID 인지)
        return permission != null && (permission.getAuthority() == PermissionType.MANAGER
                || card.getUser().getId().equals(userId));
    }
}