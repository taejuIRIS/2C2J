package com.todo.backend.scheduling;

import com.todo.backend.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class RecordScheduler {
    @Autowired
    private RecordService recordService;

    @Scheduled(cron = "0 0 0 * * *") // 매일 0시에 실행
    public void saveRecordsForTodo() {
        recordService.saveRecordsForTodo();
    }

    /*@Scheduled(cron = "0 0 0 * * *")
    public void saveRecordsForRoutinAndChallenge() {
        // 시작 날짜: 어제 자정, 종료 날짜: 오늘 자정
        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDate today = LocalDate.now();
        recordService.saveRecordsForRoutinAndChallenge(yesterday, today);
    }*/
}
