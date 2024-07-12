package com.example.demo.domain.user.controller;

import com.example.demo.domain.user.dto.RefreshTokenRequestDto;
import com.example.demo.domain.user.dto.SignupRequestDto;
import com.example.demo.domain.user.service.UserService;
import com.example.demo.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return new ResponseEntity<>("회원가입이 성공적으로 완료되었습니다.", HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(@RequestBody RefreshTokenRequestDto request, HttpServletResponse res) {
        userService.accessTokenReissue(request.getRefreshToken(), res);
        return new ResponseEntity<>("토큰 재발급 성공", HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        userService.logout(userDetails);
        return new ResponseEntity<>("로그아웃 성공" , HttpStatus.OK);
    }
}
