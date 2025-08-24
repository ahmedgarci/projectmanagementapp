package com.example.demo.Infrastructure.Security;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.Domain.models.User;

@Service
public class SecurityUtils {
    
    public static User getConnectedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User connectedUser = (User)authentication.getPrincipal();
        return connectedUser;
    }
}
