package com.todo.backend.controller.response;

import com.todo.backend.entity.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class TodoResponsedto {
    // 어떤 데이터를 받아올건지 정의
    private int id;
    private String title;
    private String content;
    private String memo;
    private String dotype;

    @Builder
    public TodoResponsedto(TodoEntity todo) { // TodoEntity의 데이터를 Responsedto에 빌드
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.content = todo.getContent();
        this.memo = todo.getMemo();
        this.dotype = todo.getDotype().name();
    }

    public static TodoResponsedto fromTodo(TodoEntity todo) { // 투두 데이터를 가져올 때 사용할 함수
        return new TodoResponsedto(todo);
    }

    public static List<TodoResponsedto> fromTodoList(List<TodoEntity> todo) { // 리스트로 변환
        return todo.stream()
                .map(TodoResponsedto::fromTodo)
                .collect(Collectors.toList());
    }
}
