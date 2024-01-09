package com.todo.backend.repository;

import com.todo.backend.entity.RecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RecordRepository extends JpaRepository<RecordEntity, Integer> {
    List<RecordEntity> findByDate(LocalDate date);
    List<RecordEntity> findByTodo(int todoId, LocalDate date);
    List<RecordEntity> findByRoutin(int routinId, LocalDate date);
    List<RecordEntity> findByChallenge(int challengeId, LocalDate date);
}
