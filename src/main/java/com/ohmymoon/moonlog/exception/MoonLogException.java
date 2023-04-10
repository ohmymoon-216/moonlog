package com.ohmymoon.moonlog.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class MoonLogException extends RuntimeException{

    private final Map<String, String> validation = new HashMap<>();

    public MoonLogException(String message) {
        super(message);
    }

    public abstract int statusCode();

    public void addValidation(String filedName, String message){
        validation.put(filedName, message);
    }
}
