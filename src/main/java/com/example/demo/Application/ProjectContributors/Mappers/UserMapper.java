package com.example.demo.Application.ProjectContributors.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.Application.ProjectContributors.Responses.ContributorDetailsResponse;
import com.example.demo.Domain.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "email",target = "userEmail")
    @Mapping(source = "jobPos",target = "jobPos")
    @Mapping(source = "publicId",target = "publicId")
    ContributorDetailsResponse FromUserToContributorDetailsResponse(User user);
}
