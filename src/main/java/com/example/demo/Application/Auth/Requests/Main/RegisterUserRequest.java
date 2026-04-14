package com.example.demo.Application.Auth.Requests.Main;

import com.example.demo.Application.Auth.Requests.VO.PositionVO;
import com.example.demo.Application.Auth.Requests.VO.UserEmailVO;
import com.example.demo.Application.Auth.Requests.VO.UserPasswordVO;
import com.example.demo.Application.Auth.Requests.VO.UsernameVO;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserRequest {
    @Valid
    private PositionVO positionVo;
    @Valid
    private UsernameVO userIdentifiersVo;
    @Valid
    private UserEmailVO userEmailVo;
    @Valid
    private UserPasswordVO userPasswordVo;
}
