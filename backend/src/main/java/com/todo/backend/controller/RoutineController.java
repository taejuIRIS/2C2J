package com.todo.backend.controller;

import com.todo.backend.controller.request.RoutineRequestdto;
import com.todo.backend.controller.response.RoutineResponsedto;
import com.todo.backend.entity.RoutineEntity;
import com.todo.backend.service.RoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routin")
public class RoutineController {

    @Autowired
    private RoutineService routineService;

    @GetMapping
    public List<RoutineResponsedto> getAllRoutin() {
        List<RoutineEntity> routine = routineService.getAllRoutine();
        return RoutineResponsedto.fromRoutineList(routine);
    }

    // 투두 추가
    @PostMapping
    public RoutineEntity addRoutine(@RequestBody RoutineRequestdto routineRequest) {
        return routineService.addRoutine(routineRequest);
    }

    // 수정
    @PutMapping("/{id}") // 투두 id를 식별하여 수행, 밑의 @PathVariable 어노테이션이 위의 id를 찾는 것
    public RoutineEntity updateRoutine(@PathVariable int id, @RequestBody RoutineRequestdto routineRequest) {
        return routineService.updateRoutine(id, routineRequest);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public void deleteRoutine(@PathVariable int id) {
        routineService.deleteRoutine(id);
    }
}