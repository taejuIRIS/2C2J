package com.todo.backend.repository;

import com.todo.backend.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TodoRepository extends JpaRepository<TodoEntity, Integer> {
    // 해당 날짜의 todo 찾기(리스트로 반환)
    List<TodoEntity> findByStartdate(LocalDate date);
}
