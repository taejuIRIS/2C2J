package com.todo.backend.controller.response;

import com.todo.backend.entity.ChallengeEntity;
import com.todo.backend.entity.LogEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class LogResponsedto {
    private int id;
    private LocalDateTime date;
    private Boolean complete;
    private Integer todoId;
    private Integer challengeId;
    private Integer routineId;

    @Builder
    public LogResponsedto(LogEntity todo) {
        this.id = todo.getId();
        this.date = todo.getDate();
        this.complete = todo.getComplete();
        this.todoId = todo.getTodo().getId();
        this.challengeId = todo.getChallenge().getId();
        this.routineId = todo.getRoutine().getId();
    }

    public static LogResponsedto fromLog(LogEntity log) {
        return new LogResponsedto(log);
    }

    public static List<LogResponsedto> fromLogList(List<LogEntity> log) { //리스트로 변환
        return log.stream()
                .map(LogResponsedto::fromLog)
                .collect(Collectors.toList());
    }
}
