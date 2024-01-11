package com.todo.backend.repository;

import com.todo.backend.entity.RoutinEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RoutinRepository extends JpaRepository<RoutinEntity, Integer> {
    //List<RoutinEntity> findByStartdateBetweenAndEnddateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
