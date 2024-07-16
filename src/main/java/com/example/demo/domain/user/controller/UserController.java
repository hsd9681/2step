package com.example.demo.domain.user.controller;

import com.example.demo.domain.permission.PermissionService;
import com.example.demo.domain.user.dto.RefreshTokenRequestDto;
import com.example.demo.domain.user.dto.SignupRequestDto;
import com.example.demo.domain.user.dto.UserResponseDto;
import com.example.demo.domain.user.service.UserService;
import com.example.demo.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PermissionService permissionService;

    @GetMapping("/login-page")
    public ModelAndView login(ModelAndView mv){
        mv.setViewName("login");
        return mv;
    }
    @GetMapping("/signup-page")
    public ModelAndView signup(ModelAndView mv){
        mv.setViewName("signup");
        return mv;
    }
    @GetMapping("/main-page")
    public ModelAndView main(ModelAndView mv){
        mv.setViewName("main");
        return mv;
    }

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
    // user 찾기
    @GetMapping("/{boardId}/users")
    public ResponseEntity<List<UserResponseDto>> getUsersByBoard(@PathVariable Long boardId) {
        List<UserResponseDto> users = permissionService.getUsersByBoard(boardId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
