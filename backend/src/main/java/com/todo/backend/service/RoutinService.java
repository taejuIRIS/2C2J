package com.todo.backend.service;

import com.todo.backend.entity.RoutinEntity;
import com.todo.backend.repository.RoutinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RoutinService {

    @Autowired
    private RoutinRepository routinRepository;

    /*public List<RoutinEntity> getRoutinByDateRange(LocalDate startDate, LocalDate endDate) {
        return routinRepository.findByStartdateBetweenAndEnddateBetween(startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
    }*/
}
