package com.example.demo.domain.comment;

import com.example.demo.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 카드 ID에 해당하는 댓글을 생성일지 기준으로 내침차순 정렬하여 반환
    // findByCardId: cardId를 기준으로 댓글을 조회
    // OrderByCreatedAtDesc: createdAt 필드를 기준으로 내림차순 정렬
    List<Comment> findByCardIdOrderByCreatedAtDesc(Long cardId);
}