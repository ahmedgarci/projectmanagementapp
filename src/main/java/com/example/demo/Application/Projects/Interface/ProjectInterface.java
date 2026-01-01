package com.example.demo.Application.Projects.Interface;

import java.util.List;


import com.example.demo.Application.Projects.Requests.Main.CreateProjectRequest;
import com.example.demo.Application.Projects.Requests.VO.ProjectPublicIdVO;
import com.example.demo.Application.Projects.Responses.ProjectDetailsResponse;
import com.example.demo.Application.Projects.Responses.UserProjectsStats;
import com.example.demo.Application.Tasks.Responses.UserTasksStats;

public interface ProjectInterface {

    void CreateNewProject(CreateProjectRequest createProjectRequest);
    void DeleteProject(ProjectPublicIdVO publicIdVO);
    ProjectDetailsResponse getProjectDetails(String publicId );
    List<ProjectDetailsResponse> getAllUserProjectsDetails();
    UserProjectsStats getUserProjectsStats();
}   
