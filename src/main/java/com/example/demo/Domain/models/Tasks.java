package com.example.demo.Domain.models;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.demo.Application.Tasks.Constants.TasksConstants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@NamedQuery(
    name = TasksConstants.FIND_CHILD_TASKS,
    query = "SELECT t FROM Tasks t WHERE t.parentTask.id = :parentTask_id"
)
@NamedQuery(
    name = TasksConstants.FIND_USER_RPROJECT_TASKS,
    query = "SELECT t FROM Tasks t WHERE (project.id = :projectId AND user.id = :userId ) "
)
@NamedQuery(
    name = TasksConstants.GET_USER_ALL_PROJECTS_URGENT_TASKS,
    query = "SELECT t FROM Tasks t WHERE (t.stage.id=2 AND user.id = :userId) ORDER BY finishingDate  LIMIT 5 "
)

public class Tasks {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;  


    @ManyToOne
    @JoinColumn(name = "parent_task",nullable = true , updatable = true)
    private Tasks parentTask;

    @Column(name ="task", nullable = false)
    private String task;

    @CreatedDate
    private LocalDate createdAt;
    
    private LocalDate startingAt;

    @Column(name = "task_ending_date",nullable = false)
    private LocalDate finishingDate;
   
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne 
    @JoinColumn(name = "stage_id")   
    private Stages stage ;
}