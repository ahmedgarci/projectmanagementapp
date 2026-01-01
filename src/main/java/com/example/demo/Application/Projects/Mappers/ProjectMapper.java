package com.example.demo.Application.Projects.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.example.demo.Application.Projects.Requests.Main.CreateProjectRequest;
import com.example.demo.Application.Projects.Responses.ProjectDetailsResponse;
import com.example.demo.Domain.models.Project;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProjectMapper {


    @Mapping(source = "endingDateVo.endingDate",target = "endingDate")
    @Mapping(source = "startingDateVo.startingDate",target = "startingDate")
    @Mapping(source = "projectDescroptionVo.description",target = "projectDescription")
    @Mapping(source = "projectNameVo.name",target = "projectName")
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "publicId",ignore = true)
    Project fromCreateProjectRequestToProject(CreateProjectRequest request);
 
    @Mapping(source = "publicId",target = "projectId")
    @Mapping(source = "projectName",target = "projectName")
    @Mapping(source = "projectDescription",target = "description")
    @Mapping(source = "startingDate",target = "startedAt")
    @Mapping(source = "endingDate",target = "endsAt")
    @Mapping(source = "stage.stageName", target = "stage")
    ProjectDetailsResponse fromProjectToProjectDetailsResponse(Project project);

}