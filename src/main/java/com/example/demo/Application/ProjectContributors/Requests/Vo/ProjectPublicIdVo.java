package com.example.demo.Application.ProjectContributors.Requests.Vo;

import jakarta.validation.constraints.NotBlank;

public record ProjectPublicIdVo(

    @NotBlank(message = "project id is required")
    String projectPublicId
) {
    
}
