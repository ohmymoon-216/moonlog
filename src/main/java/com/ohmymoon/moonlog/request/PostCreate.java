package com.ohmymoon.moonlog.request;

import com.ohmymoon.moonlog.exception.InvalidResponseException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class PostCreate {

    @NotBlank(message = "제목을 입력해 주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해 주세요.")
    private String content;

    @Builder
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void validate(){
        if(title==null || title.length()<2) throw new InvalidResponseException("title", "제목은 두자 이상이어야 합니다.");
        else if(content==null || content.length()<2) throw new InvalidResponseException("content", "내용은 두자 이상이어야 합니다.");
    }
}
