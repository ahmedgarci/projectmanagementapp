package com.example.demo.Application.Tasks.Requests.Main;

import com.example.demo.Application.Tasks.Requests.Vo.ParentTaskPublicIdVO;
import com.example.demo.Application.Tasks.Requests.Vo.TaskPublicIdVO;

import lombok.Getter;

@Getter
public class SetNodeParentRequest {
    private ParentTaskPublicIdVO source;
    private TaskPublicIdVO destination;    
}