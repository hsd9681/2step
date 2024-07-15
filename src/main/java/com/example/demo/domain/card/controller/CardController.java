package com.example.demo.domain.card.controller;


import com.example.demo.domain.board.service.BoardService;
import com.example.demo.domain.card.dto.CardRequestDto;
import com.example.demo.domain.card.dto.CardResponseDto;
import com.example.demo.domain.card.service.CardService;
import com.example.demo.domain.column.entity.BoardColumn;
import com.example.demo.domain.column.service.ColumnService;
import com.example.demo.domain.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;
    private final ColumnService columnService;

    @GetMapping("/card")
    public ResponseEntity<List<CardResponseDto>> getCards() {
        return ResponseEntity.status(HttpStatus.OK).body(cardService.getCards());
    }

    @PostMapping("/card")
    public ResponseEntity<CardResponseDto> createCard(@RequestBody @Valid CardRequestDto requestDto ,@AuthenticationPrincipal UserDetails userDetails) {
        BoardColumn boardColumn = columnService.findStatus(requestDto.getStatus());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cardService.createCard(requestDto, userDetails.getUsername(), boardColumn));
    }
    @PutMapping("/card/{cardId}")
    public ResponseEntity<CardResponseDto> updateCard(@PathVariable Long cardId,@RequestBody @Valid CardRequestDto requestDto ,@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.status(HttpStatus.OK)
                .body(cardService.updateCard(cardId, requestDto, userDetails.getUsername()));
    }
    @DeleteMapping("/card/{cardId}")
    public ResponseEntity<String> deleteCard(@PathVariable Long cardId) {
        cardService.deleteCard(cardId);
        return ResponseEntity.status(HttpStatus.OK).body("카드가 성공적으로 삭제되었습니다.");
    }
}