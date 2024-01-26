package com.todo.backend.service;

import com.todo.backend.controller.request.TodoRequestdto;
import com.todo.backend.entity.DoType;
import com.todo.backend.entity.TodoEntity;
import com.todo.backend.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
        todoEntity.setLastData(todoRequest.getLastData());

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
        todoEntity.setLastData(todoRequest.getLastData());

        return todoRepository.save(todoEntity);
    }

    public void deleteTodo(int id) { // 삭제
        todoRepository.deleteById(id);
    }

    public LocalDateTime getLastData() {
        return todoRepository.findLastData();
    }

    // 가장 최신 데이터 이후 저장된 데이터
    public List<TodoRequestdto> filterAddedData(List<TodoRequestdto> localTodos, LocalDateTime serverLastSaved) {
        return localTodos.stream()
                .filter(todo -> todo.getLastData().isAfter(serverLastSaved) || todo.getLastData().isEqual(serverLastSaved))
                .collect(Collectors.toList());
    }

    // 가장 최신 데이터 이후 수정된 데이터는 나중에
    public List<TodoRequestdto> filterModifiedAfter(List<TodoRequestdto> localTodos, LocalDateTime serverLastModified) {
        return localTodos.stream()
                .filter(todo -> todo.getLastData().isAfter(serverLastModified))
                .collect(Collectors.toList());
    }
}
