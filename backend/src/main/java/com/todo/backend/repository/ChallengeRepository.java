package com.todo.backend.repository;

import com.todo.backend.entity.ChallengeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ChallengeRepository extends JpaRepository<ChallengeEntity, Integer> {
    List<ChallengeEntity> findByStartdateBetweenAndEnddateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
