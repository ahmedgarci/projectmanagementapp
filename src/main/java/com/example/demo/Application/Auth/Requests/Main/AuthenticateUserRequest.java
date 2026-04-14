package com.example.demo.Application.Auth.Requests.Main;

import com.example.demo.Application.Auth.Requests.VO.UserEmailVO;
import com.example.demo.Application.Auth.Requests.VO.UserPasswordVO;

import jakarta.validation.Valid;
import lombok.Getter;

@Getter
public class AuthenticateUserRequest {
    @Valid
    private UserEmailVO userEmailVo;
    @Valid
    private UserPasswordVO userPasswordVo;

}
