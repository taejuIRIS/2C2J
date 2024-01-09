package com.todo.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "record")
@NoArgsConstructor
public class RecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int num;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "todo_id")
    private TodoEntity todo;

    @ManyToOne
    @JoinColumn(name = "routin_id")
    private RoutinEntity routin;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private ChallengeEntity challenge;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean complete;
}
