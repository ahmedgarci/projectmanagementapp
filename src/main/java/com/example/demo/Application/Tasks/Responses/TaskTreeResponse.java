package com.example.demo.Application.Tasks.Responses;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.Domain.models.Tasks;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskTreeResponse {
    private String taskId;
    private String task ; 
    private LocalDate taskEndingDate;
    private LocalDate taskStartingDate;
    private List<TaskTreeResponse> children;
    private String stage;

    public TaskTreeResponse(Tasks taks){
        this.taskId = taks.getId();
        this.taskEndingDate = taks.getFinishingDate();
        this.taskStartingDate = taks.getStartingAt();
        this.task = taks.getTask();
        this.children = new ArrayList<>();
        this.stage = taks.getStage().getStageName();
    }
}
