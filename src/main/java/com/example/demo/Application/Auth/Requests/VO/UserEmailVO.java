package com.example.demo.Application.Auth.Requests.VO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserEmailVO(
    @Email(message = "email value must be valid")
    @NotBlank(message = "email value is required")
    String email
) {
    
}
