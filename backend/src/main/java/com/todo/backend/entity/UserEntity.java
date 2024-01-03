package com.todo.backend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class UserEntity {
    // 테이블 설정
    /*@Id
    @Column(nullable = false)
    private String id;
    @Column(nullable = false)
    private String pwd;
    @Column(nullable = false)
    private String name;

    // 기본적으로 디폴트 값이 있으면 nullable = false로 설정된다고 합니다
    private int followers = 0; // 기본값 0
    private int followings = 0;
    private LocalDateTime birthday;

    @Builder
    public UserEntity(String id, String pwd, String name, LocalDateTime birthday) {
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.birthday = birthday;
    }

    public static UserEntity save(String id, String pwd, String name, LocalDateTime birthday) {
        return new UserEntity(id, pwd, name, birthday);
    }*/
}
