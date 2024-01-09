package com.todo.backend.service;

import com.todo.backend.entity.ChallengeEntity;
import com.todo.backend.entity.RecordEntity;
import com.todo.backend.entity.RoutinEntity;
import com.todo.backend.entity.TodoEntity;
import com.todo.backend.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RecordService {
    @Autowired
    private TodoService todoService;
    @Autowired
    private RoutinService routinService;
    @Autowired
    private ChallengeService challengeService;
    @Autowired
    private RecordRepository recordRepository;

    public void saveRecordsForTodo() {
        LocalDate today = LocalDate.now();

        // TodoEntity 조회
        List<TodoEntity> todos = todoService.getTodoByDate(today);
        for (TodoEntity todo : todos) {
            RecordEntity record = new RecordEntity();
            record.setDate(today);
            recordRepository.save(record);
        }
    }

    /*public void saveRecordsForRoutinAndChallenge(LocalDate startDate, LocalDate endDate) {
        // startDate부터 endDate까지 각 날짜에 해당하는 RoutinEntity와 ChallengeEntity 데이터를 조회
        List<RoutinEntity> routinsForDateRange = routinService.getRoutinByDateRange(startDate, endDate);
        List<ChallengeEntity> challengesForDateRange = challengeService.getChallengeByDateRange(startDate, endDate);

        // RoutinEntity에 대한 처리
        for (RoutinEntity routin : routinsForDateRange) {
            for (LocalDate date = routin.getStartdate().toLocalDate(); !date.isAfter(endDate); date = date.plusDays(1)) {
                // 여기에서 RecordEntity에 저장하는 로직을 구현
                RecordEntity record = new RecordEntity();
                record.setRoutin(routin);
                record.setDate(date);
                recordRepository.save(record);
            }
        }

        // ChallengeEntity에 대한 처리
        for (ChallengeEntity challenge : challengesForDateRange) {
            for (LocalDate date = challenge.getStartdate().toLocalDate(); !date.isAfter(endDate); date = date.plusDays(1)) {
                // 여기에서 RecordEntity에 저장하는 로직을 구현
                RecordEntity record = new RecordEntity();
                record.setChallenge(challenge);
                record.setDate(date);
                recordRepository.save(record);
            }
        }
    }*/
}
