package com.example.demo.Application.Tasks.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.Application.Tasks.Requests.Main.AssignTaskRequest;
import com.example.demo.Application.Tasks.Responses.TaskReponse;
import com.example.demo.Domain.models.Project;
import com.example.demo.Domain.models.Tasks;
import com.example.demo.Domain.models.User;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaksMapper  {
    
    @Mapping(source = "request.taskVo.task", target = "task")
    @Mapping(source = "request.taskDateVo.startingDate", target = "startingAt")
    @Mapping(source = "request.taskDateVo.endingDate", target = "finishingDate")
    @Mapping(source = "assignedTo",target = "user")
    @Mapping(source = "project",target = "project")
    @Mapping(source = "parentTask",target = "parentTask")
    @Mapping(target = "id",ignore = true)
    @Mapping(target="stage",ignore = true)
    Tasks fromAssignTaskRequestToTaskEntity(AssignTaskRequest request,Tasks parentTask, User assignedTo,Project project);


    @Mapping(source = "task",target = "task")
    @Mapping(source = "startingAt",target = "startingDate")
    @Mapping(source = "finishingDate",target = "endingDate")
    @Mapping(source = "id",target = "taskId")
    @Mapping(source = "parentTask.id",target = "taskParentId")
    TaskReponse fromTaskToFullTaskResponse(Tasks task);


    @Mapping(source = "task",target = "task")
    @Mapping(source = "startingAt",target = "startingDate")
    @Mapping(source = "finishingDate",target = "endingDate")
    @Mapping(target = "taskId",ignore = true)
    @Mapping(target = "taskParentId",ignore = true)
    TaskReponse fromTaskToBasicTaskResponse(Tasks task);

}
