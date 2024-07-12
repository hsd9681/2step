package com.example.demo.domain.card.controller;


import com.example.demo.domain.card.dto.CardRequestDto;
import com.example.demo.domain.card.dto.CardResponseDto;
import com.example.demo.domain.card.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @GetMapping("/card")
    public ResponseEntity<List<CardResponseDto>> getCards() {
        return ResponseEntity.status(HttpStatus.OK).body(cardService.getCards());
    }

    @PostMapping("/card")
    public ResponseEntity<CardResponseDto> createCard(@RequestBody @Valid CardRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cardService.createCard(requestDto));
    }
    @PutMapping("/card/{cardId}")
    public ResponseEntity<CardResponseDto> updateCard(@PathVariable Long cardId,@RequestBody @Valid CardRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.OK)
                .body(cardService.updateCard(cardId, requestDto));
    }
    @DeleteMapping("/card/{cardId}")
    public ResponseEntity<String> deleteCard(@PathVariable Long cardId) {
        cardService.deleteCard(cardId);
        return ResponseEntity.status(HttpStatus.OK).body("카드가 성공적으로 삭제되었습니다.");
    }
}