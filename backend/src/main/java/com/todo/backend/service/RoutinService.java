package com.todo.backend.service;

import com.todo.backend.controller.request.RoutinRequestdto;
import com.todo.backend.entity.DoType;
import com.todo.backend.entity.RoutinEntity;
import com.todo.backend.repository.RoutinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

@Service
public class RoutinService {

    @Autowired
    private RoutinRepository routinRepository;

    public List<RoutinEntity> getAllRoutin() {
        return routinRepository.findAll();
    }

    public RoutinEntity addRoutin(RoutinRequestdto routinRequest) { // 추가
        RoutinEntity routinEntity = new RoutinEntity();
        routinEntity.setTitle(routinRequest.getTitle());
        routinEntity.setContent(routinRequest.getContent());
        routinEntity.setMemo(routinRequest.getMemo());
        routinEntity.setDotype(DoType.valueOf(routinRequest.getDotype().toUpperCase()));
        routinEntity.setStartdate(routinRequest.getStartdate());
        routinEntity.setEnddate(routinRequest.getEnddate());

        return routinRepository.save(routinEntity);
    }

    public RoutinEntity updateRoutin(int id, RoutinRequestdto routinRequest) { // 수정
        RoutinEntity routinEntity = routinRepository.findById(id)
                // 예외처리: 해당 id의 routin가 없다면
                .orElseThrow(() -> new EntityNotFoundException("routin with id " + id + " not found"));

        routinEntity.setTitle(routinRequest.getTitle());
        routinEntity.setContent(routinRequest.getContent());
        routinEntity.setMemo(routinRequest.getMemo());
        routinEntity.setDotype(DoType.valueOf(routinRequest.getDotype().toUpperCase()));
        routinEntity.setStartdate(routinRequest.getStartdate());
        routinEntity.setEnddate(routinRequest.getEnddate());

        return routinRepository.save(routinEntity);
    }

    public void deleteRoutin(int id) { // 삭제
        routinRepository.deleteById(id);
    }

    /*public List<RoutinEntity> getRoutinByDateRange(LocalDate startDate, LocalDate endDate) {
        return routinRepository.findByStartdateBetweenAndEnddateBetween(startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
    }*/
}
