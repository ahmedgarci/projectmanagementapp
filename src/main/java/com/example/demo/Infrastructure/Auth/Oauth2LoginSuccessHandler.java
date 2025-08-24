package com.example.demo.Infrastructure.Auth;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.demo.Domain.Repository.UserRepository;
import com.example.demo.Domain.models.User;
import com.example.demo.Infrastructure.Security.JwtService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Oauth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
            OAuth2User oAuth2User =  (OAuth2User)authentication.getPrincipal();
            final String email = oAuth2User.getAttribute("email");
            System.out.println(oAuth2User.getAttributes());
            String provider = ((OAuth2AuthenticationToken)authentication).getAuthorizedClientRegistrationId();
            User user = userRepository.findByEmail(email).orElseGet(()->{
                User u = User.builder().fullName("a").jobPos("").email(email).build();
                return userRepository.save(u);
            });            
        }

}