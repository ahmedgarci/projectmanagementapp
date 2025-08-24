package com.example.demo.Application.Auth.Requests.Main;

import com.example.demo.Application.Auth.Requests.VO.UserEmailVO;
import com.example.demo.Application.Auth.Requests.VO.UserPasswordVO;

import lombok.Getter;

@Getter
public class AuthenticateUserRequest {
    private UserEmailVO userEmailVo;
    private UserPasswordVO userPasswordVo;

}
