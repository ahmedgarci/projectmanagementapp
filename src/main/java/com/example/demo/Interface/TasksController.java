package com.example.demo.Interface;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Application.Tasks.Interface.TasksInterface;
import com.example.demo.Application.Tasks.Requests.Main.AssignTaskRequest;
import com.example.demo.Application.Tasks.Requests.Main.SetNodeParentRequest;
import com.example.demo.Application.Tasks.Requests.Vo.TaskPublicIdVO;
import com.example.demo.Application.Tasks.Responses.TaskReponse;
import com.example.demo.Application.Tasks.Responses.TaskTreeResponse;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/tasks")
public class TasksController {
    private final TasksInterface tasksService;

    @PostMapping("/create")
    public ResponseEntity<?> assignNewTask(@RequestBody AssignTaskRequest request) {
        tasksService.AssignTaskToContributor(request);
        return new ResponseEntity<>("task assigned to user ", HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<?> removeTask(@RequestBody TaskPublicIdVO taskPublicIdVO){
        tasksService.RemoveProjectTask(taskPublicIdVO);
        return new ResponseEntity<>("task deleted", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{projectPublicId}")
    public ResponseEntity<List<TaskTreeResponse>> getProjectTsks(@PathVariable(name = "projectPublicId") String projectPublicId) {
        return ResponseEntity.ok(tasksService.getAllProjectTasks(projectPublicId));
    }

    @GetMapping("/user/{projectPublicId}")
    public ResponseEntity<List<TaskReponse>> getUserTasksByProject(@PathVariable(name = "projectPublicId") String projectPublicId) {
        return ResponseEntity.ok(tasksService.getAllUserProjectTasks(projectPublicId));
    }

    @GetMapping("/urgent")
    public ResponseEntity<List<TaskReponse>> getUserUrgentTasks() {
        return ResponseEntity.ok(tasksService.getUserUrgentTasks());
    }

    @PostMapping("/markParent")
    public ResponseEntity<?> setTaskParent(@RequestBody SetNodeParentRequest request) {
        return new ResponseEntity<>(tasksService.markParentForTask(request), HttpStatus.OK);
    }
    
    @PostMapping("/{taskId}/completed")
    public ResponseEntity<?> setTaskAsFinished(@PathVariable(name = "taskId") String taskId) {
        tasksService.SetTaskAsFinished(taskId);
        return  ResponseEntity.ok("task marked as finished !");
    }


}