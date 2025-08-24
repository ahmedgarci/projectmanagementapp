package com.example.demo.Application.Auth.Requests.VO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UserPasswordVO(
    @NotBlank(message = "password is required")
    @Min(value = 8,message = "password length must be at least 8 chars")
    String password
) {
    
}
