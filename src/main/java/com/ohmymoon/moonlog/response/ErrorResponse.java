package com.ohmymoon.moonlog.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * {
 *     "code": "400",
 *     "message": "잘못된 요청입니다.",
 *     "validation": {
 *         "title": "제목을 입력해주세요."
 *     }
 *
 * }
 */
@Getter
public class ErrorResponse {

    private final String code;
    private final String message;

    private Map<String, String> validation;

    public void addValidation(String fieldName, String message){
        validation.put(fieldName, message);
    }

    @Builder
    public ErrorResponse(String code, String message, Map<String, String> validation) {
        this.code = code;
        this.message = message;
        this.validation = validation!=null ? validation : new HashMap<>();
    }

}
