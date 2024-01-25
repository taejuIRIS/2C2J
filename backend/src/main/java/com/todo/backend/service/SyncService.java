package com.todo.backend.service;

import com.todo.backend.controller.request.RoutineRequestdto;
import com.todo.backend.entity.RoutineEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// 여기에 투두, 챌린지 동기화도 같이 작성
@Service
public class SyncService {

    @Autowired
    private RoutineService routineService;

    // 루틴 동기화 데이터
    public List<RoutineEntity> synchronizeWithServer(List<RoutineRequestdto> localRoutines) {
        // 가장 최신 데이터의 lastData를 서버로부터 가져옴
        LocalDateTime serverLastData = routineService.getLastData();

        // 최신 데이터 이후 추가된 데이터 필터링
        List<RoutineRequestdto> addedData = routineService.filterAddedData(localRoutines, serverLastData);

        // 최신 데이터 이후 수정된 데이터 필터링
        // List<RoutineRequestdto> modifiedData = routineService.filterModifiedAfter(localRoutines, serverLastData);

        // 추가된 데이터를 서버에 전송 및 저장
        addRoutineToServer(addedData);

        // 수정된 데이터를 서버에 전송 및 저장
        // updateDataOnServer(modifiedData);

        // 동기화된 루틴 엔티티 반환
        return routineService.getAllRoutine();
    }

    // 루틴 추가 데이터 동기화
    private void addRoutineToServer(List<RoutineRequestdto> addedData) {
        for (RoutineRequestdto routine : addedData) {
            routineService.addRoutine(routine);
        }
    }

    // 투두 동기화 데이터

    // 투두 추가 데이터 동기화

    // 챌린지 동기화 데이터

    // 챌린지 추가 데이터 동기화

    // 수정 데이터 동기화는 나중에...
    /*private void updateDataOnServer(List<RoutineRequestdto> modifiedData) {
        for (RoutineRequestdto routine : modifiedData) {
            routineService.updateRoutine(routine.getId(), routine);
        }
    }*/
}
