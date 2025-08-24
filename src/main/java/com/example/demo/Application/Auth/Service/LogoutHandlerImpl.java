package com.example.demo.Application.Auth.Service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.example.demo.Domain.Repository.TokenRepository;
import com.example.demo.Domain.models.Token;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogoutHandlerImpl implements LogoutHandler {
    private final TokenRepository tokenRepository;
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        var header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")){
            return;
        }
        String jwt = header.substring(7);
        Token token = tokenRepository.findByToken(jwt).orElseThrow(()->new EntityNotFoundException("token was not found"));
        System.out.println(token.getId());
        token.setExpired(true);
        token.setRevoked(true);
        tokenRepository.save(token);
        SecurityContextHolder.clearContext();
    }
    
}
