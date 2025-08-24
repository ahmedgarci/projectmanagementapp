package com.example.demo.Application.Projects.Requests.Main;

import com.example.demo.Application.Projects.Requests.VO.EndingDateVO;
import com.example.demo.Application.Projects.Requests.VO.ProjectDescroptionVO;
import com.example.demo.Application.Projects.Requests.VO.ProjectNameVO;
import com.example.demo.Application.Projects.Requests.VO.StartingDateVO;

import lombok.Getter;

@Getter
public class CreateProjectRequest {
    
    private EndingDateVO endingDateVo;
    private ProjectNameVO projectNameVo;
    private StartingDateVO startingDateVo;
    private ProjectDescroptionVO projectDescroptionVo;
}
