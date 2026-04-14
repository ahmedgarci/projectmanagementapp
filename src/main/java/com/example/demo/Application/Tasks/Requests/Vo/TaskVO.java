package com.example.demo.Application.Tasks.Requests.Vo;

import jakarta.validation.constraints.NotEmpty;

public record TaskVO(
    @NotEmpty(message = "task should not be empty")
    String task
) {

}
