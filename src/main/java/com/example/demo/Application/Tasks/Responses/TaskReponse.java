package com.example.demo.Application.Tasks.Responses;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskReponse {
    private String task;
    private String taskId;
    private String taskParentId;
    private LocalDate startingDate;
    private String endingDate;
    private String taskStage;
}
