package com.example.demo.Domain.Repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Application.Tasks.Constants.TasksConstants;
import com.example.demo.Domain.models.Project;
import com.example.demo.Domain.models.Tasks;

@Repository
public interface TasksRepository extends CrudRepository<Tasks,String> {
//    Optional<Tasks> findById(String id);

    @Query(name = TasksConstants.FIND_CHILD_TASKS)
    List<Tasks> findChildTasks(@Param("parentTask_id")String parentTask_id);

    List<Tasks> findByProject(Project project);

    @Query(name = TasksConstants.FIND_USER_RPROJECT_TASKS)
    List<Tasks> findTasksByUserAndByProject(@Param("projectId") Long projectId , @Param("userId") Long userId);

    @Query(name = TasksConstants.GET_USER_ALL_PROJECTS_URGENT_TASKS)
    List<Tasks> findAllUserUrgetTasks(@Param("userId") Long userId);
}
