package com.example.demo.domain.user.service;

import com.example.demo.common.exception.CustomException;
import com.example.demo.common.exception.ErrorCode;
import com.example.demo.domain.user.dto.SignupRequestDto;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import com.example.demo.security.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public void signup(SignupRequestDto requestDto) {
        String encryptPassword = passwordEncoder.encode(requestDto.getPassword());
        boolean isUser = userRepository.existsByUsername(requestDto.getUsername());
        if(isUser) {
            throw new NullPointerException("이미 회원가입이 된 사용자 입니다.");
        }
        userRepository.save(User.builder()
                .username(requestDto.getUsername())
                .password(encryptPassword)
                .build());
    }

    public String accessTokenReissue(String refreshToken, HttpServletResponse res) {
        Claims info = jwtUtil.getUserInfoFromToken(jwtUtil.refreshTokenSubstring(refreshToken));

        User user = userRepository.findByUsername(info.getSubject()).orElseThrow(
                () -> new NullPointerException("조회된 회원의 정보가 없습니다.")
        );

        String newAccessToken = jwtUtil.createAccessToken(user);
        String newRefreshToken = jwtUtil.createRefreshToken(user);
        res.addHeader(JwtUtil.ACCESS_TOKEN_HEADER, newAccessToken);
        res.addHeader(JwtUtil.REFRESH_TOKEN_HEADER, newRefreshToken);
        String newRefreshTokenOriginal = jwtUtil.refreshTokenSubstring(newRefreshToken);
        user.updateRefreshToken(newRefreshTokenOriginal);

        return newAccessToken;
    }

    @Transactional
    public void logout(UserDetailsImpl userDetails) {
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(
                () -> new NullPointerException("조회된 회원의 정보가 없습니다.")
        );
        user.updateRefreshToken(null);
    }

    // user 찾기
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND));
    }

}
