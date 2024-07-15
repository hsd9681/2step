package com.example.demo.domain.comment.service;

import com.example.demo.common.exception.CustomException;
import com.example.demo.common.exception.ErrorCode;
import com.example.demo.domain.board.entity.Board;
import com.example.demo.domain.board.repository.BoardRepository;
import com.example.demo.domain.card.entity.Card;
import com.example.demo.domain.card.service.CardService;
import com.example.demo.domain.column.entity.BoardColumn;
import com.example.demo.domain.column.repository.ColumnRepository;
import com.example.demo.domain.comment.dto.CommentRequestDto;
import com.example.demo.domain.comment.dto.CommentResponseDto;
import com.example.demo.domain.comment.entity.Comment;
import com.example.demo.domain.comment.repository.CommentRepository;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final ColumnRepository columnRepository;
    private final CardService cardService;
    private final UserService userService;


    // 댓글 작성 기능
    public CommentResponseDto createComment(Long cardId, CommentRequestDto requestDto, String username) {

        // 로그인하지 않은 사용자 예외 처리
        if (username == null) {
            throw new CustomException(ErrorCode.USER_NOT_LOGGED_IN_TO_CREATE_COMMENT);
        }

        User user = userService.findUserByUsername(username);
        Card card = cardService.findById(cardId);

        // 삭제된 카드 예외 처리
        if (card.isDeleted()) {
            throw new CustomException(ErrorCode.CANNOT_COMMENT_ON_DELETED_CARD);
        }

        // 권한 확인
        if (cardService.hasPermissionForCard(user.getId(), cardId)) {
            // 권한이 있는 경우에 수행할 작업: 댓글 생성 및 저장
            Comment comment = new Comment(requestDto.getContent(), card, user);
            Comment savedComment = commentRepository.save(comment);
            return convertToResponseDto(savedComment);
        } else {
            throw new CustomException(ErrorCode.USER_NOT_MANAGER);
        }
    }


    // 특정 카드 댓글 조회 기능
    @Transactional(readOnly = true)
    public List<CommentResponseDto> getCommentsByCardId(Long cardId, String username) {

        // 로그인하지 않은 사용자 예외 처리
        if (username == null) {
            throw new CustomException(ErrorCode.USER_NOT_LOGGED_IN_TO_VIEW_COMMENTS);
        }

        User user = userService.findUserByUsername(username);
        Card card = cardService.findById(cardId);

        // 삭제된 카드 예외 처리
        if (card.isDeleted()) {
            throw new CustomException(ErrorCode.CANNOT_VIEW_COMMENTS_OF_DELETED_CARD);
        }

        // 권한 확인
        if (cardService.hasPermissionForCard(user.getId(), cardId)) {
            // 권한이 있는 경우에 수행할 작업: 댓글 조회
            return commentRepository.findByCardIdOrderByCreatedAtDesc(cardId).stream()
                    .map(this::convertToResponseDto)
                    .collect(Collectors.toList());
        } else {
            throw new CustomException(ErrorCode.USER_NOT_MANAGER);
        }
    }

    // 초대 받은 모든 보드에서 생성된 카드의 댓글 조회
    @Transactional(readOnly = true)
    public List<CommentResponseDto> getAllCommentsFromInvitedBoards(String username) {
        // 로그인하지 않은 사용자 예외 처리
        if (username == null) {
            throw new CustomException(ErrorCode.USER_NOT_LOGGED_IN_TO_VIEW_COMMENTS);
        }

        User user = userService.findUserByUsername(username);

        // 초대받은 모든 보드를 조회
        List<Board> invitedBoards = boardRepository.findByPermissions_User_Id(user.getId());

        // 각 보드에서 생성된 카드의 댓글을 조회
        List<CommentResponseDto> allComments = new ArrayList<>();
        for (Board board : invitedBoards) {
            List<BoardColumn> columns = columnRepository.findAllByBoardOrdersByOrder(board);
            for (BoardColumn column : columns) {
                for (Card card : column.getCards()) {
                    List<Comment> comments = commentRepository.findByCardIdOrderByCreatedAtDesc(card.getId());
                    allComments.addAll(comments.stream()
                            .map(this::convertToResponseDto)
                            .toList());
                }
            }
        }

        return allComments;
    }

    // 해당 메소드를 통해 엔티티를 DTO로 변환
    private CommentResponseDto convertToResponseDto(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .username(comment.getUser().getUsername())
                .build();
    }
}