package com.todo.backend.service;

import com.todo.backend.controller.request.RoutineRequestdto;
import com.todo.backend.entity.DoType;
import com.todo.backend.entity.RoutineEntity;
import com.todo.backend.repository.RoutineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.util.List;

@Service
public class RoutineService {

    @Autowired
    private RoutineRepository routineRepository;

    public List<RoutineEntity> getAllRoutine() {
        return routineRepository.findAll();
    }

    public RoutineEntity addRoutine(RoutineRequestdto routineRequest) { // 추가
        RoutineEntity routineEntity = new RoutineEntity();
        routineEntity.setTitle(routineRequest.getTitle());
        routineEntity.setContent(routineRequest.getContent());
        routineEntity.setMemo(routineRequest.getMemo());
        routineEntity.setDotype(DoType.valueOf(routineRequest.getDotype().toUpperCase()));
        routineEntity.setStartdate(routineRequest.getStartdate());
        routineEntity.setEnddate(routineRequest.getEnddate());
        routineEntity.setSelecteddays(routineRequest.getSelecteddaysBitset());

        return routineRepository.save(routineEntity);
    }

    public RoutineEntity updateRoutine(int id, RoutineRequestdto routineRequest) { // 수정
        RoutineEntity routineEntity = routineRepository.findById(id)
                // 예외처리: 해당 id의 routin가 없다면
                .orElseThrow(() -> new EntityNotFoundException("routin with id " + id + " not found"));
        routineEntity.setTitle(routineRequest.getTitle());
        routineEntity.setContent(routineRequest.getContent());
        routineEntity.setMemo(routineRequest.getMemo());
        routineEntity.setDotype(DoType.valueOf(routineRequest.getDotype().toUpperCase()));
        routineEntity.setStartdate(routineRequest.getStartdate());
        routineEntity.setEnddate(routineRequest.getEnddate());
        routineEntity.setSelecteddays(routineRequest.getSelecteddaysBitset());

        return routineRepository.save(routineEntity);
    }

    public void deleteRoutine(int id) { // 삭제
        routineRepository.deleteById(id);
    }

    /*public List<RoutineEntity> getRoutinByDateRange(LocalDate startDate, LocalDate endDate) {
        return routinRepository.findByStartdateBetweenAndEnddateBetween(startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
    }*/
}
