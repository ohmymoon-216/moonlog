package com.ohmymoon.moonlog.exception;

public class PostNotFoundException extends MoonLogException{

    public static final String MESSAGE = "존재하지 않는 글입니다.";
    public PostNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int statusCode() {
        return 404;
    }
}
