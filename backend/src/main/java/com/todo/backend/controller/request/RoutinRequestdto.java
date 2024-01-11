package com.todo.backend.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class RoutinRequestdto {
    private String title;
    private String content;
    private String memo;
    private String dotype;
    private LocalDateTime startdate;
    private LocalDateTime enddate;
}
