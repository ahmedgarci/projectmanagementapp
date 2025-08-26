package com.example.demo.Application.Tasks.Interface;

import java.util.List;


import com.example.demo.Application.Tasks.Requests.Main.AssignTaskRequest;
import com.example.demo.Application.Tasks.Requests.Main.SetNodeParentRequest;
import com.example.demo.Application.Tasks.Responses.TaskReponse;
import com.example.demo.Application.Tasks.Responses.TaskTreeResponse;

public interface TasksInterface {
    void AssignTaskToContributor(AssignTaskRequest request);    
    void RemoveProjectTask(String taskPublicId);
    void SetTaskAsFinished(String taskPublicId);
    List<TaskReponse> getUserUrgentTasks();
    List<TaskTreeResponse> getAllProjectTasks(String projectPublicIdVo);
    List<TaskReponse> getAllUserProjectTasks(String  projectPublicIdVo);
    boolean markParentForTask(SetNodeParentRequest request);
    

}
