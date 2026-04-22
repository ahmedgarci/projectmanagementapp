package com.example.demo.Infrastructure.Security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.Domain.Repository.TokenRepository;
import com.example.demo.Domain.models.Token;
import com.example.demo.Global.CustomExceptions.CustomJWTexpiredException;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class RequestFilterConfig extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;
    private final RestAuthenticationEntryPoint authenticationEntryPoint;
    @Override

    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        
        final String authHeader = request.getHeader("Authorization");
        final String token;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Authorization header is  invalid.");
            authenticationEntryPoint.commence(request, response, new CustomJWTexpiredException("jwt token is required"));
            return;
        }

        token = authHeader.substring(7);

        try {
            username = jwtService.extractUsernameFromToken(token);
        } catch (ExpiredJwtException e) {
            markTokenAsExpired(token);
            authenticationEntryPoint.commence(request, response,
            new CustomJWTexpiredException("Token expired"));
            return; 
       } catch (Exception e) {
            log.error("Failed to extract username from token.", e);
            authenticationEntryPoint.commence(request, response,
            new CustomJWTexpiredException("invalid token"));
            return; 
        }

        if (username == null) {
            authenticationEntryPoint.commence(request, response,
                new CustomJWTexpiredException("Invalid token: username missing"));
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<Token> savedTokenOpt = tokenRepository.findByToken(token);
            if (savedTokenOpt.isPresent()) {
                Token savedToken = savedTokenOpt.get();

                if (!savedToken.isExpired() && !savedToken.isRevoked()) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    if (jwtService.isTokenValid(token, userDetails)) {
                        Authentication auth = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }else{
                        authenticationEntryPoint.commence(request, response,
                        new CustomJWTexpiredException("Invalid token"));
                        return;
                    }
                } else {
                    log.warn("Token is either expired or revoked.");
                    authenticationEntryPoint.commence(request, response,
                    new CustomJWTexpiredException("Token expired or revoked"));
                    return;
                }
            } else {
                log.warn("Token not found in database.");
                authenticationEntryPoint.commence(request, response,
                    new CustomJWTexpiredException("Token not recognized"));
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private void markTokenAsExpired(String token) {
        tokenRepository.findByToken(token).ifPresent(storedToken -> {
            storedToken.setExpired(true);
            tokenRepository.save(storedToken);
        });
    }
}
