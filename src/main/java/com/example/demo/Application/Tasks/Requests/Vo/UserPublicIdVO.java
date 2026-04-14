package com.example.demo.Application.Tasks.Requests.Vo;

import jakarta.validation.constraints.NotEmpty;

public record UserPublicIdVO(
    @NotEmpty(message = "task must be assigned to user")
    String userPublicId
) {
    
}
