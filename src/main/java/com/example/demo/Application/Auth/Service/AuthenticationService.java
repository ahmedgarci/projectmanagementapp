package com.example.demo.Application.Auth.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Application.Auth.Interface.AuthInterface;
import com.example.demo.Application.Auth.Requests.Main.AuthenticateUserRequest;
import com.example.demo.Application.Auth.Requests.Main.RegisterUserRequest;
import com.example.demo.Application.Auth.Responses.AuthenticatedResponse;
import com.example.demo.Domain.Repository.TokenRepository;
import com.example.demo.Domain.Repository.UserRepository;
import com.example.demo.Domain.models.Token;
import com.example.demo.Domain.models.User;
import com.example.demo.Global.CustomExceptions.EntityAlreadyExistsException;
import com.example.demo.Infrastructure.Security.JwtService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.RequiredArgsConstructor;
import lombok.var;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthInterface {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;

    @Override
    public void RegisterNewUser(RegisterUserRequest registerUserRequest) {
        Optional<User> userOpt = userRepository.findByEmail(registerUserRequest.getUserEmailVo().email());
        if(userOpt.isPresent()){
            throw new EntityAlreadyExistsException("user already exists");
        }
        User user = User.builder().email(registerUserRequest.getUserEmailVo().email())
        .fullName(registerUserRequest.getUserIdentifiersVo().fullName())
        .jobPos(registerUserRequest.getPositionVo().jobPosition())
        .password(passwordEncoder.encode(registerUserRequest.getUserPasswordVo().password()))
        .build();
        userRepository.save(user);
    }


    @Override
    public AuthenticatedResponse AuthenticateUser(AuthenticateUserRequest authenticateUserRequest) {
        try {
            var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticateUserRequest.getUserEmailVo().email(),
            authenticateUserRequest.getUserPasswordVo().password()));      
            User user = (User)authentication.getPrincipal();
            this.RevokeAllUserValidToken(user.getId());
            String authenticationToken = jwtService.generateToken(user,Map.of("id",user.getPublicId()));
            Token token = Token.builder().token(authenticationToken).user(user).build();
            tokenRepository.save(token);
            return AuthenticatedResponse.builder().token("Bearer "+authenticationToken).jobPos(user.getJobPos()).username(user.getFullName()).build();
        } catch (BadCredentialsException badCredentialsException) {
            throw new BadCredentialsException("bad credentials");
        }
        

    }

    private void RevokeAllUserValidToken(Long userId){
       List<Token> tokens =  tokenRepository.findAllValidUserTokens(userId);
       if(tokens.isEmpty()){return;}
       tokens.forEach((t)-> {t.setRevoked(true);t.setExpired(true);});
        tokenRepository.saveAll(tokens);
    }

}
