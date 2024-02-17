package com.todo.backend.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class LogRequestdto {
    private LocalDateTime date;
    private Boolean complete;
    private Integer todoId;
    private Integer challengeId;
    private Integer routineId;
}
