package com.example.demo.Application.Projects.Requests.VO;

import jakarta.validation.constraints.NotBlank;

public record ProjectNameVO(

    @NotBlank(message = "project name value should be present")
    String name
) {
    
}
