package com.example.demo.Application.Projects.Responses;



public class TasksStatResponse {
    private Long allTasks;
    private Long completedTasks;

    public TasksStatResponse(Long allTasks, Long completedTasks) {
        this.allTasks = allTasks;
        this.completedTasks = completedTasks;
    }

    public Long getAllTasks() {
        return allTasks;
    }

    public Long getCompletedTasks() {
        return completedTasks;
    }

}
