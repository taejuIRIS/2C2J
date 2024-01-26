package com.todo.backend.repository;

import com.todo.backend.entity.RecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RecordRepository extends JpaRepository<RecordEntity, Integer> {
}
