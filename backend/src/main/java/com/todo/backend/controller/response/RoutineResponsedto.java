package com.todo.backend.controller.response;

import com.todo.backend.entity.RoutineEntity;
import com.todo.backend.service.DayOfWeekConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class RoutineResponsedto {
    private int id;
    private String title;
    private String content;
    private String memo;
    private String dotype;
    private LocalDateTime startdate;
    private LocalDateTime enddate;
    private DayOfWeek[] selecteddays;

    @Builder
    public RoutineResponsedto(RoutineEntity routine) { // RoutinEntity의 데이터를 Responsedto에 빌드
        this.id = routine.getId();
        this.title = routine.getTitle();
        this.content = routine.getContent();
        this.memo = routine.getMemo();
        this.dotype = routine.getDotype().name();
        this.startdate = routine.getStartdate();
        this.enddate = routine.getEnddate();
        this.selecteddays = DayOfWeekConverter.convertToDays(routine.getSelecteddays());
    }

    public static RoutineResponsedto fromRoutine(RoutineEntity routine) { // 루틴 데이터를 가져올 때 사용할 함수
        return new RoutineResponsedto(routine);
    }

    public static List<RoutineResponsedto> fromRoutineList(List<RoutineEntity> routine) { // 리스트로 변환
        return routine.stream()
                .map(RoutineResponsedto::fromRoutine)
                .collect(Collectors.toList());
    }

}
