package com.example.demo.Application.Projects.Requests.VO;

import jakarta.validation.constraints.NotBlank;

public record ProjectDescroptionVO(

    @NotBlank(message = "project description should be present")
    String description
) {
    
}
