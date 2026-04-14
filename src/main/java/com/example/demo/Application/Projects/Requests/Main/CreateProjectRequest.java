package com.example.demo.Application.Projects.Requests.Main;

import com.example.demo.Application.Projects.Requests.VO.EndingDateVO;
import com.example.demo.Application.Projects.Requests.VO.ProjectDescroptionVO;
import com.example.demo.Application.Projects.Requests.VO.ProjectNameVO;
import com.example.demo.Application.Projects.Requests.VO.StartingDateVO;

import jakarta.validation.Valid;
import lombok.Getter;

@Getter
public class CreateProjectRequest {
    @Valid
    private EndingDateVO endingDateVo;
    @Valid
    private ProjectNameVO projectNameVo;
    @Valid
    private StartingDateVO startingDateVo;
    @Valid
    private ProjectDescroptionVO projectDescroptionVo;
}
