package com.example.demo.Application.Auth.Interface;

import com.example.demo.Application.Auth.Requests.Main.AuthenticateUserRequest;
import com.example.demo.Application.Auth.Requests.Main.RegisterUserRequest;
import com.example.demo.Application.Auth.Responses.AuthenticatedResponse;

public interface AuthInterface {
    
    void RegisterNewUser(RegisterUserRequest registerUserRequest);

    AuthenticatedResponse AuthenticateUser(AuthenticateUserRequest authenticateUserRequest);

}
