package com.example.demo.Interface;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Application.ProjectContributors.Interface.ProjectContributorsManagementInterface;
import com.example.demo.Application.ProjectContributors.Requests.Main.AddContributorRequest;
import com.example.demo.Application.ProjectContributors.Responses.ContributorDetailsResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RequestMapping(value = "/contributors")
@RequiredArgsConstructor
@RestController
public class ProjectContributorsController {

    private final ProjectContributorsManagementInterface projectContributorService;

    @PostMapping
    public ResponseEntity<String> addNewContributorToProject(@RequestBody @Valid  AddContributorRequest addContributorRequest,Authentication authentication) {
        projectContributorService.addNewContributorToProject(addContributorRequest,authentication);        
        return new ResponseEntity<>("invitation sent !",HttpStatus.OK);
    }

    @GetMapping("/acceptinvite")
    public ResponseEntity<String> acceptInvitation(@RequestParam(name = "code") String activationCode) {
        projectContributorService.acceptProjectInvite(activationCode);
        return new ResponseEntity<>("invitation accepted",HttpStatus.OK);
    }

    @GetMapping("/{projectPublicId}")
    public ResponseEntity<List<ContributorDetailsResponse>> getAllProjectContributors(@PathVariable(name = "projectPublicId") String projectPublicId) {
        return  ResponseEntity.ok(projectContributorService.getProjectContributors(projectPublicId));
    }
    
    
    
    
}