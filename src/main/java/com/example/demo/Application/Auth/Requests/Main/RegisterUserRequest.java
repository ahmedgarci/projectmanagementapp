package com.example.demo.Application.Auth.Requests.Main;

import com.example.demo.Application.Auth.Requests.VO.PositionVO;
import com.example.demo.Application.Auth.Requests.VO.UserEmailVO;
import com.example.demo.Application.Auth.Requests.VO.UserPasswordVO;
import com.example.demo.Application.Auth.Requests.VO.UsernameVO;

import lombok.Getter;

@Getter
public class RegisterUserRequest {
    private PositionVO positionVo;
    private UsernameVO userIdentifiersVo;
    private UserEmailVO userEmailVo;
    private UserPasswordVO userPasswordVo;
}
