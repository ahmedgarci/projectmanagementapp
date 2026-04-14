package com.example.demo.Application.Tasks.Requests.Main;


import com.example.demo.Application.ProjectContributors.Requests.Vo.ProjectPublicIdVo;
import com.example.demo.Application.Tasks.Requests.Vo.ParentTaskPublicIdVO;
import com.example.demo.Application.Tasks.Requests.Vo.TaskDatesVO;
import com.example.demo.Application.Tasks.Requests.Vo.TaskVO;
import com.example.demo.Application.Tasks.Requests.Vo.UserPublicIdVO;

import jakarta.validation.Valid;
import lombok.Getter;

@Getter
public class AssignTaskRequest{
    @Valid
    private TaskVO taskVo;
    @Valid
    private ProjectPublicIdVo projectPublicIdVo;
    @Valid
    private UserPublicIdVO userIdVo;
    private TaskDatesVO taskDateVo;
    private ParentTaskPublicIdVO parentTaskPublicIdVo;
}
