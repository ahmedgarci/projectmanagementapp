package com.example.demo.Application.Projects.Responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserProjectsStats {
    private Long numberOfProjectCompleted;
    private Long numberOfProjects;
    private Long taksCompleted;
    private Long allTasks;
}
