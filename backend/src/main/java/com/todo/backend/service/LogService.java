package com.todo.backend.service;

import com.todo.backend.entity.LogEntity;
import com.todo.backend.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    public List<LogEntity> getAllLog() {
        return logRepository.findAll();
    }

    public LocalDateTime getLastData() {
        return logRepository.findLastData();
    }

    /*// 가장 최신 데이터 이후 저장된 데이터
    public List<LogRequestdto> filterAddedData(List<LogRequestdto> localLogs, LocalDateTime serverLastSaved) {
        return localLogs.stream()
                .filter(log -> log.getDate().isAfter(serverLastSaved) || log.getDate().isEqual(serverLastSaved))
                .collect(Collectors.toList());
    }*/
}
