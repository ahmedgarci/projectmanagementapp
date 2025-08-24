package com.example.demo.Application.Projects.Responses;

import java.util.List;

import com.example.demo.Domain.models.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDetailsResponse {
    
    private String projectId;

    private String projectName;

    private String startedAt;

    private String endsAt;

    private String description;

    private List<User> contributors;

    private String stage;
}
