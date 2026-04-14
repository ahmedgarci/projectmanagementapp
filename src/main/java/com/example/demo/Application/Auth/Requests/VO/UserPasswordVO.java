package com.example.demo.Application.Auth.Requests.VO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserPasswordVO(
    @NotBlank(message = "password is required")
    @Size(min  = 8,message = "password length must be at least 8 chars")
    String password
) {
    
}
