package com.example.demo.Global.CustomExceptions;

public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException(String msg){
        super(msg);
    }
    
}
