package com.todo.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "routin")
@NoArgsConstructor
public class RoutinEntity {
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

    private LocalDateTime enddate = LocalDateTime.parse("9999-12-31T23:59:59");
    //끝나는 시간의 기본값을 무한대로 설정

    @Enumerated(EnumType.STRING) // 열거형 상수의 문자열 값을 DB에 저장하고 읽음
    private DoType dotype = DoType.DO;

    /*
    @ManyToOne(fetch = FetchType.LAZY) //다대일관계
    @JoinColumn(name = "user_id")
    private UserEntity user;
    */

    private int selecteddays = 1111111;
}
