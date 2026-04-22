package com.example.demo.Application.ProjectContributors.Interface;

import java.util.List;


import com.example.demo.Application.ProjectContributors.Requests.Main.AddContributorRequest;
import com.example.demo.Application.ProjectContributors.Requests.Main.RemoveContributorRequest;
import com.example.demo.Application.ProjectContributors.Responses.ContributorDetailsResponse;

public interface ProjectContributorsManagementInterface {
    void addNewContributorToProject(AddContributorRequest addContributorRequest);
    void removeContributorFromProject(RemoveContributorRequest removeContributorRequest);
    void acceptProjectInvite(String code);
    List<ContributorDetailsResponse> getProjectContributors(String projectPublicId);

}
