package com.example.demo.Application.Projects.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.Application.Projects.Interface.ProjectInterface;
import com.example.demo.Application.Projects.Mappers.ProjectMapper;
import com.example.demo.Application.Projects.Requests.Main.CreateProjectRequest;
import com.example.demo.Application.Projects.Requests.VO.ProjectPublicIdVO;
import com.example.demo.Application.Projects.Responses.ProjectDetailsResponse;
import com.example.demo.Application.Projects.Responses.UserProjectsStats;
import com.example.demo.Domain.Repository.ProjectRepository;
import com.example.demo.Domain.Repository.StagesRepository;
import com.example.demo.Domain.Repository.UserRepository;
import com.example.demo.Domain.models.Project;
import com.example.demo.Domain.models.Stages;
import com.example.demo.Domain.models.User;
import com.example.demo.Global.CustomExceptions.EntityAlreadyExistsException;
import com.example.demo.Infrastructure.Security.SecurityUtils;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService implements ProjectInterface{

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserRepository userRepository;
    private final StagesRepository stagesRepository;

    @Override
    @Transactional
    public void CreateNewProject(CreateProjectRequest createProjectRequest) {
        Optional<Project> prjecOptional = projectRepository.findByProjectName(createProjectRequest.getProjectNameVo().name());
        if(prjecOptional.isPresent()){
            throw new EntityAlreadyExistsException("project already exists");
        }
        Project project = projectMapper.fromCreateProjectRequestToProject(createProjectRequest);
        projectRepository.save(project);
        User connectedUser = SecurityUtils.getConnectedUser();
        User user = userRepository.findById(connectedUser.getId())
        .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Stages inprogressStage = stagesRepository.findByStageName("inProgress").orElseThrow(()-> new EntityNotFoundException("stage was not found"));
        if(user.getProjects() == null || user.getProjects().isEmpty()){
            user.setProjects(new HashSet<>());
        }
        user.getProjects().add(project);
        project.setStage(inprogressStage);
        userRepository.save(user);
    }

    @Override
    public void DeleteProject(ProjectPublicIdVO projectPublicIdvo) {
        Optional<Project> projectOptional = projectRepository.findByPublicId(projectPublicIdvo.projectPublicId());   
        if(projectOptional.isPresent()){
            projectRepository.delete(projectOptional.get());
            return;
        }
        throw new EntityNotFoundException("project was not found ");
    }

    @Override
    public ProjectDetailsResponse getProjectDetails(String publicId) {
        Optional<Project> projectOptional = projectRepository.findByPublicId(publicId);   
        if(!projectOptional.isPresent()){
            throw new EntityNotFoundException("project was not found ");
        }
        return projectMapper.fromProjectToProjectDetailsResponse(projectOptional.get());
    }

    @Override
    public List<ProjectDetailsResponse> getAllUserProjectsDetails() {
        User connectedUser = SecurityUtils.getConnectedUser();
        List<Project> projects = projectRepository.findProjectsByUser(connectedUser);
        return projects.stream().map(projectMapper::fromProjectToProjectDetailsResponse).collect(Collectors.toList());
    }

    @Override
    public UserProjectsStats getUserProjectsStats(){
        User user = SecurityUtils.getConnectedUser();        
        return projectRepository.getUserProjectsStat(user.getId());
    }
    
}