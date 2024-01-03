package com.todo.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "todo")
@NoArgsConstructor
public class TodoEntity {
    @Id // 기본키로 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 생성
    private int num;

    @Column(nullable = false, length = 45) // 널 값 허용 여부, 문자열 개수(디폴트 255)
    private String title;

    @Column(nullable = false)
    private String content;

    private String name; // @Column 어노테이션은 선택적, 각 컬럼의 널 값이나 기본값 설정 등에 사용

    @Column(nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'do'")
    private String dotype;

    @ManyToOne(fetch = FetchType.LAZY) // 다대일 관계
    @JoinColumn(name = "user_id") // 외래키 지정
    private UserEntity user;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean complete;

}
