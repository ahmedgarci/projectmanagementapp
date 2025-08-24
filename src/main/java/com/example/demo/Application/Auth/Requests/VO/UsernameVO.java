package com.example.demo.Application.Auth.Requests.VO;

import jakarta.validation.constraints.NotBlank;

public record UsernameVO(
    @NotBlank(message = "full name is required")
    String fullName
) {
    
}
