package com.todo.backend.controller.response;

import com.todo.backend.entity.RoutinEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class RoutinResponsedto {
    private int id;
    private String title;
    private String content;
    private String memo;
    private String dotype;
    private LocalDateTime startdate;
    private LocalDateTime enddate;

    @Builder
    public RoutinResponsedto(RoutinEntity routin) { // RoutinEntity의 데이터를 Responsedto에 빌드
        this.id = routin.getId();
        this.title = routin.getTitle();
        this.content = routin.getContent();
        this.memo = routin.getMemo();
        this.dotype = routin.getDotype().name();
        this.startdate = routin.getStartdate();
        this.enddate = routin.getEnddate();
    }

    public static RoutinResponsedto fromroutin(RoutinEntity routin) { // 루틴 데이터를 가져올 때 사용할 함수
        return new RoutinResponsedto(routin);
    }

    public static List<RoutinResponsedto> fromroutinList(List<RoutinEntity> routin) { // 리스트로 변환
        return routin.stream()
                .map(RoutinResponsedto::fromroutin)
                .collect(Collectors.toList());
    }

}
