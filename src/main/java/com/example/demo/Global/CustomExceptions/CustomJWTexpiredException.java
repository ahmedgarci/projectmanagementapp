package com.example.demo.Global.CustomExceptions;

public class CustomJWTexpiredException extends RuntimeException {
        public CustomJWTexpiredException(String msg){
            super(msg);
        }
}
