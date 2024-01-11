package com.todo.backend.repository;

import com.todo.backend.entity.RoutineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoutineRepository extends JpaRepository<RoutineEntity, Integer> {
    //List<RoutineEntity> findByStartdateBetweenAndEnddateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
