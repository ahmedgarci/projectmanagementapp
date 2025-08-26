package com.example.demo.Application.Tasks.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.demo.Application.Tasks.Interface.TasksInterface;
import com.example.demo.Application.Tasks.Mappers.TaksMapper;
import com.example.demo.Application.Tasks.Requests.Main.AssignTaskRequest;
import com.example.demo.Application.Tasks.Requests.Main.SetNodeParentRequest;
import com.example.demo.Application.Tasks.Responses.TaskReponse;
import com.example.demo.Application.Tasks.Responses.TaskTreeResponse;
import com.example.demo.Domain.Repository.ProjectRepository;
import com.example.demo.Domain.Repository.StagesRepository;
import com.example.demo.Domain.Repository.TasksRepository;
import com.example.demo.Domain.Repository.UserRepository;
import com.example.demo.Domain.models.Project;
import com.example.demo.Domain.models.Stages;
import com.example.demo.Domain.models.Tasks;
import com.example.demo.Domain.models.User;
import com.example.demo.Infrastructure.Security.SecurityUtils;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService implements TasksInterface{

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final TaksMapper taksMapper;
    private final TasksRepository tasksRepository;
    private final StagesRepository stagesRepository;

    @Override
    @Transactional
    public void AssignTaskToContributor(AssignTaskRequest request) {
        User user = userRepository.findByPublicId(request.getUserIdVo().userPublicId()).orElseThrow(()-> new EntityNotFoundException("user was not foudn"));
        Tasks parentTask= null;
        if(request.getParentTaskPublicIdVo() != null && request.getParentTaskPublicIdVo().parentTaskPublicId() != null &&  !request.getParentTaskPublicIdVo().parentTaskPublicId().isEmpty()){
            parentTask = tasksRepository.findById(request.getParentTaskPublicIdVo().parentTaskPublicId()).orElseThrow(()-> new EntityNotFoundException("user was not foudn"));
        }
        Project project = projectRepository.findByPublicId(request.getProjectPublicIdVo().projectPublicId()).orElseThrow(()-> new EntityNotFoundException("project was not foudn"));
        Stages stage = stagesRepository.findById(Long.valueOf(3)).orElseThrow(()-> new EntityNotFoundException("stage was not found"));        
        Tasks task = taksMapper.fromAssignTaskRequestToTaskEntity(request,parentTask,user,project);
        task.setStage(stage);
        tasksRepository.save(task);
    }

    @Override
    @Transactional
    public void RemoveProjectTask(String taskPublicIdVO) {
        Tasks parentTask = tasksRepository.findById(taskPublicIdVO).orElseThrow(()-> new EntityNotFoundException("task was not foudn"));
        List<Tasks> subTasks = tasksRepository.findChildTasks(parentTask.getId());
        tasksRepository.deleteAll(subTasks);
        tasksRepository.delete(parentTask);
    }

    @Override
    public List<TaskTreeResponse> getAllProjectTasks(String projectPublicIdVo) {
        Project project =  projectRepository.findByPublicId(projectPublicIdVo).orElseThrow(()-> new EntityNotFoundException("task was not found"));
        List<Tasks> projectTasks = tasksRepository.findByProject(project);
        if(projectTasks.isEmpty()){
            return null;
        }
        return constructTasksTree(projectTasks);
    }

    @Override
    public List<TaskReponse> getAllUserProjectTasks(String projectPublicIdVO) {
        Project project =  projectRepository.findByPublicId(projectPublicIdVO).orElseThrow(()-> new EntityNotFoundException("task was not foudn"));
        User connectedUser = SecurityUtils.getConnectedUser();
        List<Tasks> userTasks = tasksRepository.findTasksByUserAndByProject(project.getId(),connectedUser.getId());
        return userTasks.stream().map(taksMapper::fromTaskToBasicTaskResponse).toList();
    }

    @Override
    public List<TaskReponse> getUserUrgentTasks() {
        User connectedUser = SecurityUtils.getConnectedUser();
        List<Tasks> userTasks = tasksRepository.findAllUserUrgetTasks(connectedUser.getId());
        return userTasks.stream().map(taksMapper::fromTaskToBasicTaskResponse).toList();
    }


    private List<TaskTreeResponse> constructTasksTree(List<Tasks> projectTasks){
        Map<String,List<Tasks>> parentIdMap = buildParentMap(projectTasks);
        List<Tasks> rootTasks = parentIdMap.get(null);
        List<TaskTreeResponse> roots = new ArrayList<>();
        for(Tasks task : rootTasks){
            TaskTreeResponse taskResponse =  buildSubtree(task,parentIdMap);
            roots.add(taskResponse);
        }
        return roots;
    }

    private TaskTreeResponse buildSubtree(Tasks currentTask,Map<String,List<Tasks>> parentMap){
        TaskTreeResponse response = new TaskTreeResponse(currentTask);
        List<Tasks> children = parentMap.get(currentTask.getId());
        if(children != null){
            for (Tasks child : children) {
                response.getChildren().add(buildSubtree(child,parentMap));
            }
        }
        return response;
    }

    private Map<String,List<Tasks>> buildParentMap(List<Tasks> projectTasks){
        Map<String,List<Tasks>> parentMap = new HashMap<>();
        for(Tasks task : projectTasks){
            String parentTaskId = task.getParentTask() != null ? task.getParentTask().getId() : null ;
            parentMap.computeIfAbsent(parentTaskId, k -> new ArrayList<>()).add(task);
        }
        return parentMap;
    }

    @Override
    public boolean markParentForTask(SetNodeParentRequest request) {
        Set<String> visited = new HashSet<>();
        List<Tasks> destNodeSubTasks = tasksRepository.findChildTasks(request.getDestination().publicId());
        for(Tasks child : destNodeSubTasks){
            if(!visited.contains(child.getId()) && DFS(child,request.getSource().parentTaskPublicId(), visited)){
                return false;
            }
        }
        Tasks sourceTask = tasksRepository.findById(request.getSource().parentTaskPublicId()).orElseThrow(()->new EntityNotFoundException("task not found"));
        Tasks destTask  = tasksRepository.findById(request.getDestination().publicId()).orElseThrow(()->new EntityNotFoundException(""));
        destTask.setParentTask(sourceTask);
        tasksRepository.save(sourceTask);
        return true;
    }
    
    private boolean DFS(Tasks source, String dest ,Set<String> visited){
        if(source.getId().equals(dest)){return true;}
        visited.add(source.getId());
        for (Tasks child : tasksRepository.findChildTasks(source.getId())) {
            if(!visited.contains(child.getId()) && DFS(child,dest,visited)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void SetTaskAsFinished(String taskPublicId) {
        Tasks task = tasksRepository.findById(taskPublicId).orElseThrow(()->new EntityNotFoundException("task was not found"));
        task.setStage(stagesRepository.findByStageName("Completed").orElseThrow(()-> new EntityNotFoundException("stage was not found")));
        tasksRepository.save(task);
    }
    
}