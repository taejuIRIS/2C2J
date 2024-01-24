package com.todo.backend.repository;

import com.todo.backend.entity.RoutineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface RoutineRepository extends JpaRepository<RoutineEntity, Integer> {

    // 가장 최신 데이터 시간 찾기
    @Query("SELECT MAX(lastData) FROM RoutineEntity")
    LocalDateTime findLastData();
}
