package com.example.demo.Interface;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Application.Auth.Interface.AuthInterface;
import com.example.demo.Application.Auth.Requests.Main.AuthenticateUserRequest;
import com.example.demo.Application.Auth.Requests.Main.RegisterUserRequest;
import com.example.demo.Application.Auth.Responses.AuthenticatedResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthInterface authInterface;

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@RequestBody @Valid RegisterUserRequest registerUserRequest) {
        authInterface.RegisterNewUser(registerUserRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticatedResponse> authenticateUser(@RequestBody @Valid AuthenticateUserRequest request) {
        return ResponseEntity.ok(authInterface.AuthenticateUser(request));
    }
    
        
    
}
