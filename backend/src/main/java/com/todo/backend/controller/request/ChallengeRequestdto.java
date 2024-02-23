package com.todo.backend.controller.request;

import com.todo.backend.service.DayOfWeekConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ChallengeRequestdto {
    //입력 데이터 정의
    private int id;
    private String title;
    private String content;
    private String memo;
    private String dotype;
    private LocalDateTime startdate;
    private LocalDateTime enddate;
    private DayOfWeek[] selecteddays; // 배열로 선택된 요일을 받음
    private LocalDateTime lastData;

    public int getSelecteddaysBitset() {
        return DayOfWeekConverter.convertToBitset(selecteddays);
    }
}
