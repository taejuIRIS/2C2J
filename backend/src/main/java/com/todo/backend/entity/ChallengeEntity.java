package com.todo.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "challenge")
@NoArgsConstructor
public class ChallengeEntity {
    @Id // 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동생성
    private int id;

    @Column(nullable = false, length = 45)
    private String title;

    @Column(nullable = false)
    private String content;

    private String memo;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime startdate; //시작시간의 기본값을 현재 시각으로 설정

    @Column(nullable = false)
    private LocalDateTime enddate;    // 끝나는 시간은 필수로 입력

    @Enumerated(EnumType.STRING) // 열거형 상수의 문자열 값을 DB에 저장하고 읽음
    private DoType dotype = DoType.DO;

    /*
    @ManyToOne(fetch = FetchType.LAZY) //다대일관계
    @JoinColumn(name = "user_id")
    private UserEntity user;
    */

    private int selecteddays = 1111111;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime lastData;
}
