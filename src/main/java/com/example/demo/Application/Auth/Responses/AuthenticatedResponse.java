package com.example.demo.Application.Auth.Responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticatedResponse {
    private String token;
    private String username;
    private String jobPos;    
}
