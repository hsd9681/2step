package com.example.demo.domain.comment;

import com.example.demo.domain.comment.dto.CommentRequestDto;
import com.example.demo.domain.comment.dto.CommentResponseDto;
import com.example.demo.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 카드 댓글 생성
    @PostMapping("/cards/{cardId}/comments")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long cardId,
                                                            @Valid @RequestBody CommentRequestDto requestDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 사용자의 정보가 null 이 아니면(르그인 O) username 에 userDetails 의 사용자 이름을 할당
        // null 이면(로그인 X) username 에 null 을 할당
        String username = userDetails != null ? userDetails.getUsername() : null;
        CommentResponseDto createdComment = commentService.createComment(cardId, requestDto, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    // 초대 받은 모든 보드에서 생성된 카드의 댓글 조회
    @GetMapping("/comments/invited")
    public ResponseEntity<List<CommentResponseDto>> getAllCommentsFromInvitedBoards(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails != null ? userDetails.getUsername() : null;
        List<CommentResponseDto> comments = commentService.getAllCommentsFromInvitedBoards(username);
        return ResponseEntity.ok(comments);
    }

    // 특정 카드 댓글 조회
    @GetMapping("/cards/{cardId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long cardId,
                                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails != null ? userDetails.getUsername() : null;
        List<CommentResponseDto> comments = commentService.getCommentsByCardId(cardId, username);
        return ResponseEntity.ok(comments);
    }
}