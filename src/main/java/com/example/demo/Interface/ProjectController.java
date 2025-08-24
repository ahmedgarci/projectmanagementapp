package com.example.demo.Interface;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Application.Projects.Interface.ProjectInterface;
import com.example.demo.Application.Projects.Requests.Main.CreateProjectRequest;
import com.example.demo.Application.Projects.Requests.VO.ProjectPublicIdVO;
import com.example.demo.Application.Projects.Responses.ProjectDetailsResponse;
import com.example.demo.Application.Projects.Responses.UserProjectsStats;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping(value = "/project")
@RequiredArgsConstructor
public class ProjectController {
    
    private final ProjectInterface projectService;

    @PostMapping
    public ResponseEntity<String> createProject(@RequestBody @Valid CreateProjectRequest createProjectRequest){
        projectService.CreateNewProject(createProjectRequest);
        return new ResponseEntity<String>("project created successfully", HttpStatus.CREATED);
    }  

    @GetMapping("/stats")
    public ResponseEntity<UserProjectsStats> getUserProjectsStats() {
        return ResponseEntity.ok(projectService.getUserProjectsStats());
    }
    

    @DeleteMapping
    public ResponseEntity<String> deleteProject(@RequestBody @Valid ProjectPublicIdVO projectPublicIdVO){
        projectService.DeleteProject(projectPublicIdVO);
        return new ResponseEntity<String>("project deleted !", HttpStatus.ACCEPTED);
    }

    @GetMapping("/{projectPublicId}")
    public ResponseEntity<ProjectDetailsResponse> getProjectDetails(@PathVariable String projectPublicId) {
        System.out.println(projectPublicId);
        return ResponseEntity.ok(projectService.getProjectDetails(projectPublicId));
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<ProjectDetailsResponse>> getAllProjects(){
        return ResponseEntity.ok(projectService.getAllUserProjectsDetails());
    }

   
        

    

}
