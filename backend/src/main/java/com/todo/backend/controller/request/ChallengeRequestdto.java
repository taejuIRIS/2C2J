package com.todo.backend.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChallengeRequestdto {
    //입력 데이터 정의
    private  String title;
    private String content;
    private String memo;
    private String dotype;
    private Boolean complete;
}
