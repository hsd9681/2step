package com.example.demo.domain.user.service;

import com.example.demo.domain.user.dto.SignupRequestDto;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

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
}
