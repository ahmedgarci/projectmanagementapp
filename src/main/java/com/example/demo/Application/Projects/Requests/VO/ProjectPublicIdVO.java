package com.example.demo.Application.Projects.Requests.VO;

import jakarta.validation.constraints.NotBlank;

public record ProjectPublicIdVO(
    @NotBlank(message = "project id value must be present")
    String projectPublicId
) {
    
}
