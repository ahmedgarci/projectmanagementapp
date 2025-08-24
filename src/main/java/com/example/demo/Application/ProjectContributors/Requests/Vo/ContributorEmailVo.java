package com.example.demo.Application.ProjectContributors.Requests.Vo;

import jakarta.validation.constraints.Email;

public record ContributorEmailVo(
    @Email(message = "contributor email must be valid")
    String contributorEmail
) {
    
}
