package com.todo.backend.service;

import com.todo.backend.controller.request.RoutineRequestdto;
import com.todo.backend.entity.DoType;
import com.todo.backend.entity.RoutineEntity;
import com.todo.backend.repository.RoutineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
        routineEntity.setLastData(routineRequest.getLastData());

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
        routineEntity.setEnddate(routineRequest.getEnddate());
        routineEntity.setSelecteddays(routineRequest.getSelecteddaysBitset());
        routineEntity.setLastData(routineRequest.getLastData());

        return routineRepository.save(routineEntity);
    }

    public void deleteRoutine(int id) { // 삭제
        routineRepository.deleteById(id);
    }

    // 실제 데이터베이스에서 가장 최신 데이터의 시간을 조회
    public LocalDateTime getLastData() {
        return routineRepository.findLastData();
    }

    // 가장 최신 데이터 이후 저장된 데이터
    public List<RoutineRequestdto> filterAddedData(List<RoutineRequestdto> localRoutines, LocalDateTime serverLastSaved) {
        return localRoutines.stream()
                .filter(routine -> routine.getLastData().isAfter(serverLastSaved) || routine.getLastData().isEqual(serverLastSaved))
                .collect(Collectors.toList());
    }

    // 가장 최신 데이터 이후 수정된 데이터는 나중에...
    /*public List<RoutineRequestdto> filterModifiedAfter(List<RoutineRequestdto> localRoutines, LocalDateTime serverLastModified) {
        return localRoutines.stream()
                .filter(routine -> routine.getLastData().isAfter(serverLastModified))
                .collect(Collectors.toList());
    }*/

}
