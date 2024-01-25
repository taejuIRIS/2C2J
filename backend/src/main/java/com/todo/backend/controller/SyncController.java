package com.todo.backend.controller;

import com.todo.backend.controller.request.RoutineRequestdto;
import com.todo.backend.controller.response.RoutineResponsedto;
import com.todo.backend.entity.RoutineEntity;
import com.todo.backend.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// 여기에 투두, 챌린지 동기화도 같이 작성
@RestController
@RequestMapping("/api/syncWithServer")
public class SyncController {

    @Autowired
    private SyncService syncService;

    // 루틴 동기화 데이터 반환
    @PostMapping("/routin")
    public List<RoutineResponsedto> syncWithServer(@RequestBody List<RoutineRequestdto> localRoutines) {
        List<RoutineEntity> synchronizedData = syncService.synchronizeWithServer(localRoutines);
        return RoutineResponsedto.fromroutineList(synchronizedData);
    }

    // 투두 동기화 데이터 반환

    // 챌린지 동기화 데이터 반환
}
