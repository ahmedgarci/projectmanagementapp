package com.example.demo.Application.Tasks.Requests.Main;


import com.example.demo.Application.ProjectContributors.Requests.Vo.ProjectPublicIdVo;
import com.example.demo.Application.Tasks.Requests.Vo.ParentTaskPublicIdVO;
import com.example.demo.Application.Tasks.Requests.Vo.TaskDatesVO;
import com.example.demo.Application.Tasks.Requests.Vo.TaskVO;
import com.example.demo.Application.Tasks.Requests.Vo.UserPublicIdVO;

import lombok.Getter;

@Getter
public class AssignTaskRequest{
    private TaskVO taskVo;
    private ProjectPublicIdVo projectPublicIdVo;
    private UserPublicIdVO userIdVo;
    private TaskDatesVO taskDateVo;
    private ParentTaskPublicIdVO parentTaskPublicIdVo;
}
