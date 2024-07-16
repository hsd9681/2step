package com.example.demo.security;

import com.example.demo.domain.user.dto.LoginRequestDto;
import com.example.demo.domain.user.dto.LoginResponseDto;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Objects;

@Slf4j(topic = "JwtAuthenticationFilter")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final AuthenticationFailureHandler failureHandler;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository, AuthenticationFailureHandler failureHandler) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.failureHandler = failureHandler;
        setFilterProcessesUrl("/api/user/login");
    }

    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException
    {
        if(!Objects.equals(request.getMethod(), "POST")) {
            throw new AuthenticationServiceException("잘못된 HTTP 요청 입니다.");
        }
        try {
            LoginRequestDto requestDto = new ObjectMapper()
                    .readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException
    {
        User user = ((UserDetailsImpl) authResult.getPrincipal()).getUser();
        String accessToken = jwtUtil.createAccessToken(user);
        String refreshToken = jwtUtil.createRefreshToken(user);

        updateRefreshToken(user, refreshToken);

        LoginResponseDto responseDto = LoginResponseDto.builder()
                .httpStatusCode(HttpStatus.OK.value())
                .message("로그인이 성공적으로 되었습니다.")
                .build();

        response.addHeader(JwtUtil.ACCESS_TOKEN_HEADER , accessToken);
        response.addHeader(JwtUtil.REFRESH_TOKEN_HEADER , refreshToken);
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseDto));
    }

    private void updateRefreshToken(User user, String refreshToken) {
        String originalRefreshToken = jwtUtil.refreshTokenSubstring(refreshToken);
        user.updateRefreshToken(originalRefreshToken);
        userRepository.save(user);
        log.info("{}", "[로그인 시점][DB] refreshToken 업데이트 성공");
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed) throws ServletException, IOException {
        failureHandler.onAuthenticationFailure(request, response, failed);
    }
}
