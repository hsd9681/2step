package com.example.demo.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
public enum ErrorCode {

    // 공통 오류 코드
    FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "실패했습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "토큰을 찾을 수 없습니다."),

    // 사용자 도메인 오류 코드
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 유저를 찾을 수 없습니다."),
    USER_NOT_UNIQUE(HttpStatus.CONFLICT, "중복된 사용자가 존재합니다."),
    EMAIL_NOT_UNIQUE(HttpStatus.CONFLICT, "중복된 이메일이 존재합니다."),
    DUPLICATE_PASSWORD(HttpStatus.BAD_REQUEST, "기존 비밀번호와 동일한 비밀번호입니다."),
    CURRENT_PASSWORD_MATCH(HttpStatus.BAD_REQUEST, "현재 비밀번호와 사용자의 비밀번호가 일치하지 않습니다."),
    SAME_NEW_PASSWORD(HttpStatus.BAD_REQUEST, "동일한 비밀번호로는 변경할 수 없습니다."),
    RECENT_PASSWORD_MATCH(HttpStatus.BAD_REQUEST, "최근 사용했던 비밀번호는 변경할 수 없습니다."),
    INCORRECT_PASSWORD(HttpStatus.UNAUTHORIZED, "입력하신 비밀번호가 일치하지 않습니다."),

    // 소셜 로그인 도메인 오류 코드
    SOCIAL_TOKEN_GET_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "해당하는 소셜 유저 토큰을 가져오는데 실패했습니다."),
    SOCIAL_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 소셜 유저 데이터를 가져오는데 실패했습니다."),

    // 게시글 도메인 오류 코드
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
    POST_NOT_USER(HttpStatus.FORBIDDEN, "해당 게시글의 작성자가 아닙니다."),
    POST_SAME_USER(HttpStatus.FORBIDDEN, "해당 게시글의 작성자입니다."),
    POST_EMPTY(HttpStatus.NO_CONTENT, "먼저 작성하여 소식을 알려보세요!"),

    // 댓글 도메인 오류 코드
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),
    COMMENT_NOT_USER(HttpStatus.FORBIDDEN, "해당 댓글의 작성자가 아닙니다."),
    COMMENT_SAME_USER(HttpStatus.FORBIDDEN, "해당 댓글의 작성자입니다."),

    // 팔로우 도메인 오류 코드
    SAME_USER(HttpStatus.BAD_REQUEST, "자신을 팔로우 할 수 없습니다."),
    ALREADY_FOLLOW(HttpStatus.BAD_REQUEST, "이미 팔로우를 하셨습니다."),
    RECENT_NOT_FOLLOW(HttpStatus.BAD_REQUEST, "이 유저를 팔로우하지 않았습니다."),
    EMPTY_FOLLOW(HttpStatus.BAD_REQUEST, "현재 팔로우한 유저가 없습니다.");

    private final HttpStatus status;
    private String msg;
}
