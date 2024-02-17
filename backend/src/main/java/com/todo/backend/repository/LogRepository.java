package com.todo.backend.repository;

import com.todo.backend.entity.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface LogRepository extends JpaRepository<LogEntity, Integer> {
    @Query("SELECT MAX(date) FROM LogEntity")
    LocalDateTime findLastData();
}
