package com.todo.backend.controller.response;

import com.todo.backend.entity.ChallengeEntity;
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
public class ChallengeResponsedto {
    //어떤 데이터를 받아올 것인지 정의
    private  int id;
    private String title;
    private String content;
    private String memo;
    private String dotype;
    private LocalDateTime startdate;
    private LocalDateTime enddate;
    private DayOfWeek[] selecteddays;
    private LocalDateTime lastData;

    @Builder
    public ChallengeResponsedto(ChallengeEntity challenge) { //ChallengeEntity의 데이터를 Responsedto에 빌드
        this.id = challenge.getId();
        this.title = challenge.getTitle();
        this.content = challenge.getContent();
        this.memo = challenge.getMemo();
        this.dotype = challenge.getDotype().name();
        this.startdate = challenge.getStartdate();
        this.enddate = challenge.getEnddate();
        this.selecteddays = DayOfWeekConverter.convertToDays(challenge.getSelecteddays());
        this.lastData = challenge.getLastData();
    }

    public static ChallengeResponsedto fromChallenge(ChallengeEntity challenge) {
        return new ChallengeResponsedto(challenge);
    }

    public static List<ChallengeResponsedto> fromChallengeList(List<ChallengeEntity> challenge) { //리스트로 변환
        return challenge.stream()
                .map(ChallengeResponsedto::fromChallenge)
                .collect(Collectors.toList());
    }
}
