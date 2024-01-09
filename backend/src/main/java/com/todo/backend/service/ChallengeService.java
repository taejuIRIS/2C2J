package com.todo.backend.service;

import com.todo.backend.entity.ChallengeEntity;
import com.todo.backend.repository.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ChallengeService {

    @Autowired
    private ChallengeRepository challengeRepository;

    public List<ChallengeEntity> getChallengeByDateRange(LocalDate startDate, LocalDate endDate) {
        return challengeRepository.findByStartdateBetweenAndEnddateBetween(startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
    }
}
