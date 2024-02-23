package com.todo.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "log")
@NoArgsConstructor
public class LogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private LocalDateTime date; // 마지막 저장 시각: 서버 데이터와 비교

    @ManyToOne
    @JoinColumn(name = "todo_id")
    private TodoEntity todo;

    @ManyToOne
    @JoinColumn(name = "routine_id")
    private RoutineEntity routine;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private ChallengeEntity challenge;

    @ManyToOne(fetch = FetchType.LAZY) //다대일관계
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean complete; // 성공/실패 여부 체크
}
