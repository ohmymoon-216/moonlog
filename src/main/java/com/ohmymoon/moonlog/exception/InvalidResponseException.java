package com.ohmymoon.moonlog.exception;

import lombok.Getter;

@Getter
public class InvalidResponseException extends MoonLogException{

    public static final String MESSAGE = "잘못된 요청입니다.";

    public InvalidResponseException() {
        super(MESSAGE);
    }

    public InvalidResponseException(String fieldName, String message){
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int statusCode() {
        return 400;
    }
}
