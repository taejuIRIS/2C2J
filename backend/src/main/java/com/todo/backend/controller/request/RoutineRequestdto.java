package com.todo.backend.controller.request;

import com.todo.backend.service.DayOfWeekConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class RoutineRequestdto {
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
