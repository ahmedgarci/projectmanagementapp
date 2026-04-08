package com.example.demo.Global.CustomExceptions;

import org.springframework.security.core.AuthenticationException;

public class CustomJWTexpiredException extends AuthenticationException {
        public CustomJWTexpiredException(String msg){
            super(msg);
        }
}
