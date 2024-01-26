package com.todo.backend.service;

import com.todo.backend.controller.request.ChallengeRequestdto;
import com.todo.backend.controller.request.RoutineRequestdto;
import com.todo.backend.controller.request.TodoRequestdto;
import com.todo.backend.entity.ChallengeEntity;
import com.todo.backend.entity.RoutineEntity;
import com.todo.backend.entity.TodoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// 여기에 투두, 챌린지 동기화도 같이 작성
@Service
public class SyncService {

    @Autowired
    private RoutineService routineService;
    @Autowired
    private TodoService todoService;
    @Autowired
    private ChallengeService challengeService;

    // 루틴 동기화 데이터
    public List<RoutineEntity> synchronizeRoutineWithServer(List<RoutineRequestdto> localRoutines) {
        // todo하고 challenge의 경우와 구별하기 위해 synchronizeRoutineWithServer으로 바꿈 0_0;;
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
    public List<TodoEntity> synchronizeTodoWithServer(List<TodoRequestdto> localTodos) {
        // 가장 최신 데이터의 lastData를 서버로부터 가져옴
        LocalDateTime serverLastData = todoService.getLastData();

        // 최신 데이터 이후 추가된 데이터 필터링
        List<TodoRequestdto>  addedData = todoService.filterAddedData(localTodos, serverLastData);

        // 최신 데이터 이후 수정된 데이터 필터링
        //List<TodoRequestdto> modifiedData = todoService.filterModifiedAfter(localTodos, serverLastData);

        // 추가된 데이터를 서버에 전송 및 저장
        addTodoToServer(addedData);

        // 수정된 데이터를 서버에 전송 및 저장
        //updateDataOnServer(modifiedData);

        // 동기화된 투두 엔티티 반환
        return todoService.getAllTodo();
    }

    // 투두 추가 데이터 동기화
    private void addTodoToServer(List<TodoRequestdto> addedData) {
        for (TodoRequestdto todo : addedData) {
            todoService.addTodo(todo);
        }
    }
    // 챌린지 동기화 데이터
    public List<ChallengeEntity> synchronizeChallengeWithServer(List<ChallengeRequestdto> localChallenges) {
        // 가장 최신 데이터의 lastData를 서버로부터 가져옴
        LocalDateTime serverLastdata = challengeService.getLastData();

        // 최신 데이터 이후 추가된 데이터 필터링
        List<ChallengeRequestdto> addedData = challengeService.filterAddedData(localChallenges, serverLastdata);

        // 최신 데이터 이후 수정된 데이터 필터링
        //List<ChallengeRequestdto> modifiedData = challengeService.filterModifiedAfter(localChallenges, serverLastdata);

        // 추가된 데이터를 서버에 전송 및 저장
        addChallengeToServer(addedData);

        // 수정된 데이터를 서버에 전송 및 저장
        //updateDataOnServer(modifiedData);

        // 동기화된 챌린지 엔티티 반환
        return challengeService.getAllChallenge();

    }
    // 챌린지 추가 데이터 동기화
    private void addChallengeToServer(List<ChallengeRequestdto> addedData) {
        for (ChallengeRequestdto challenge : addedData) {
            challengeService.addChallenge(challenge);
        }
    }

    // 수정 데이터 동기화는 나중에...
    /*private void updateDataOnServer(List<RoutineRequestdto> modifiedData) {
        for (RoutineRequestdto routine : modifiedData) {
            routineService.updateRoutine(routine.getId(), routine);
        }
    }*/
    /*private void updateDataOnServer(List<TodoRequestdto> modifiedData) {
        for (TodoRequestdto todo : modifiedData) {
            todoService.updateTodo(todo.getId(), todo);
        }
    }*/
    /*private void updateDataOnServer(List<ChallengeRequestdto> modifiedData) {
        for (ChallengeRequestdto challenge : modifiedData) {
            challengeService.updateChallenge(challenge.getId(),challenge);
        }
    }*/
}
