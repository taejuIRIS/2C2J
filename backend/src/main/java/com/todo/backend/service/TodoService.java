package com.todo.backend.service;

import com.todo.backend.controller.request.TodoRequestdto;
import com.todo.backend.entity.DoType;
import com.todo.backend.entity.TodoEntity;
import com.todo.backend.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    // TodoEntity의 데이터를 리스트로 저장
    public List<TodoEntity> getAllTodo() {
        return todoRepository.findAll();
    }

    /*// TodoEntity의 데이터 자정마다 초기화
    @Scheduled(cron = "0 0 0 * * *") // 매일 자정 (cron 표현식)
    public void reset() {
        todoRepository.deleteAll();
    }*/

    public TodoEntity addTodo(TodoRequestdto todoRequest) { // 추가
        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setTitle(todoRequest.getTitle());
        todoEntity.setContent(todoRequest.getContent());
        todoEntity.setMemo(todoRequest.getMemo());
        todoEntity.setDotype(DoType.valueOf(todoRequest.getDotype().toUpperCase()));

        return todoRepository.save(todoEntity);
    }

    public TodoEntity updateTodo(int id, TodoRequestdto todoRequest) { // 수정
        TodoEntity todoEntity = todoRepository.findById(id)
                // 예외처리: 해당 id의 todo가 없다면
                .orElseThrow(() -> new EntityNotFoundException("Todo with id " + id + " not found"));

        todoEntity.setTitle(todoRequest.getTitle());
        todoEntity.setContent(todoRequest.getContent());
        todoEntity.setMemo(todoRequest.getMemo());
        todoEntity.setDotype(DoType.valueOf(todoRequest.getDotype().toUpperCase()));

        return todoRepository.save(todoEntity);
    }

    public void deleteTodo(int id) { // 삭제
        todoRepository.deleteById(id);
    }

    public List<TodoEntity> getTodoByDate(LocalDate date) {
        // TodoRepository의 findByDate 메서드를 호출하여 해당 날짜에 시작하는 TodoEntity 목록 반환
        return todoRepository.findByStartdate(date);
    }
}
