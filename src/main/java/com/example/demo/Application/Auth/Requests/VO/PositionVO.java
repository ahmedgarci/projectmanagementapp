package com.example.demo.Application.Auth.Requests.VO;

import jakarta.validation.constraints.NotBlank;

public record PositionVO(
    @NotBlank(message = "job position is required")
    String jobPosition
) {
    
}
