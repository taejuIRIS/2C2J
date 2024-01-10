package com.todo.backend.controller;

import com.todo.backend.controller.request.RoutinRequestdto;
import com.todo.backend.controller.response.RoutinResponsedto;
import com.todo.backend.entity.RoutinEntity;
import com.todo.backend.service.RoutinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class RoutinController {

    @Autowired
    private RoutinService routinService;

    @GetMapping
    public List<RoutinResponsedto> getAllRoutin() {
        List<RoutinEntity> todo = routinService.getAllRoutin();
        return RoutinResponsedto.fromroutinList(todo);
    }

    // 투두 추가
    @PostMapping
    public RoutinEntity addRoutin(@RequestBody RoutinRequestdto routinRequest) {
        return routinService.addRoutin(routinRequest);
    }

    // 수정
    @PutMapping("/{id}") // 투두 id를 식별하여 수행, 밑의 @PathVariable 어노테이션이 위의 id를 찾는 것
    public RoutinEntity updateRoutin(@PathVariable int id, @RequestBody RoutinRequestdto routinRequest) {
        return routinService.updateRoutin(id, routinRequest);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable int id) {
        routinService.deleteRoutin(id);
    }
}