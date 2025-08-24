package com.example.demo.Application.ProjectContributors.Requests.Main;

import com.example.demo.Application.ProjectContributors.Requests.Vo.ContributorEmailVo;
import com.example.demo.Application.ProjectContributors.Requests.Vo.ProjectPublicIdVo;

import lombok.Getter;

@Getter
public class AddContributorRequest {
    private ContributorEmailVo contributorEmailVo;
    private ProjectPublicIdVo projectPublicIdVo;
}
