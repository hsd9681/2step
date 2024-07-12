package com.example.demo.security;

import com.example.demo.domain.user.dto.LoginRequestDto;
import com.example.demo.domain.user.dto.LoginResponseDto;
import com.example.demo.domain.user.entity.User;
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
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Objects;

@Slf4j(topic = "JwtAuthenticationFilter")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
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

        LoginResponseDto responseDto = LoginResponseDto.builder()
                .httpStatusCode(HttpStatus.OK.value())
                .message("로그인이 성공적으로 되었습니다.")
                .build();

        response.addHeader(JwtUtil.ACCESS_TOKEN_HEADER , accessToken);
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseDto));
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed) {
        System.out.println("비밀번호가 잘못 된듯");
        response.setStatus(401);
    }
}
