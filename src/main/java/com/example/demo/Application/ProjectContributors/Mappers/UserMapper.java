package com.example.demo.Application.ProjectContributors.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.example.demo.Application.ProjectContributors.Responses.ContributorDetailsResponse;
import com.example.demo.Domain.models.User;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(source = "email",target = "userEmail")
    @Mapping(source = "jobPos",target = "jobPos")
    @Mapping(source = "publicId",target = "publicId")
    ContributorDetailsResponse FromUserToContributorDetailsResponse(User user);
}
