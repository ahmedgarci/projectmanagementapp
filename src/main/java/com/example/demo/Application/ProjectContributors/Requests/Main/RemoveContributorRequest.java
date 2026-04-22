package com.example.demo.Application.ProjectContributors.Requests.Main;

public record RemoveContributorRequest(
    String projectPublicId,
    String contributorPublicId
) {
    
}
