package com.example.demo.Domain.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Application.Projects.Responses.UserProjectsStats;
import com.example.demo.Domain.Constants.ProjectConstants;
import com.example.demo.Domain.models.Project;
import com.example.demo.Domain.models.User;

public interface ProjectRepository extends JpaRepository<Project,Long>{
    Optional<Project> findByProjectName(String projectName);   
    Optional<Project> findByPublicId(String publicId);

    @Query(name=ProjectConstants.findProjectsByUser)
    List<Project> findProjectsByUser(@Param("user") User user);
    
    @Query("""
        SELECT new com.example.demo.Application.Projects.Responses.UserProjectsStats(
            SUM(CASE WHEN p.stage.stageName = 'Completed' THEN 1 ELSE 0 END),
            COUNT(DISTINCT p),
            SUM(CASE WHEN t.stage.stageName = 'Completed' THEN 1 ELSE 0 END),
            COUNT(t)
        )
        FROM Project p
        JOIN p.users u
        JOIN p.projectTasks t
        WHERE u.id = :userId
    """)
    UserProjectsStats getUserProjectsStat(@Param("userId") Long userId );

}