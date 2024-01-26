package com.todo.backend.service;

import com.todo.backend.controller.request.ChallengeRequestdto;
import com.todo.backend.entity.ChallengeEntity;
import com.todo.backend.entity.DoType;
import com.todo.backend.repository.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChallengeService {

    @Autowired
    private ChallengeRepository challengeRepository;

    //ChallengeEntity의 데이터를 리스트로 저장
    public  List<ChallengeEntity> getAllChallenge() {
        return challengeRepository.findAll();
    }

    public ChallengeEntity addChallenge(ChallengeRequestdto challengeRequest) { // 추가
        ChallengeEntity challengeEntity = new ChallengeEntity();
        challengeEntity.setTitle(challengeRequest.getTitle());
        challengeEntity.setContent(challengeRequest.getContent());
        challengeEntity.setMemo(challengeRequest.getMemo());
        challengeEntity.setDotype(DoType.valueOf(challengeRequest.getDotype().toUpperCase()));
        challengeEntity.setStartdate(challengeRequest.getStartdate());
        challengeEntity.setEnddate(challengeRequest.getEnddate());
        challengeEntity.setSelecteddays(challengeRequest.getSelecteddaysBitset());
        challengeEntity.setLastData(challengeRequest.getLastData());

        return challengeRepository.save(challengeEntity);
    }

    public ChallengeEntity updateChallenge(int id, ChallengeRequestdto challengeRequest) { //수정
        ChallengeEntity challengeEntity = challengeRepository.findById(id)
                //예외처리: 해당 id의 challenge가 없다면
                .orElseThrow(() -> new EntityNotFoundException("Challenge with id " + id + " not found"));
        challengeEntity.setTitle(challengeRequest.getTitle());
        challengeEntity.setContent(challengeRequest.getContent());
        challengeEntity.setMemo(challengeRequest.getMemo());
        challengeEntity.setDotype(DoType.valueOf(challengeRequest.getDotype().toUpperCase()));
        challengeEntity.setEnddate(challengeRequest.getEnddate());
        challengeEntity.setStartdate(challengeRequest.getStartdate()); // 챌린지는 시작날짜도 중요하므로 수정 가능하게끔
        challengeEntity.setEnddate(challengeRequest.getEnddate());
        challengeEntity.setSelecteddays(challengeRequest.getSelecteddaysBitset());
        challengeEntity.setLastData(challengeRequest.getLastData());

        return challengeRepository.save(challengeEntity);
    }

    public void deleteChallenge(int id) {
        challengeRepository.deleteById(id);
    }

    // 실제 데이터베이스에서 가장 최신 데이터의 시간을 조회
    public LocalDateTime getLastData() {
        return challengeRepository.findLastData();
    }

    // 가장 최신 데이터 이후 저장된 데이터
    public List<ChallengeRequestdto> filterAddedData(List<ChallengeRequestdto> localChallenges, LocalDateTime serverLastsaved) {
        return localChallenges.stream()
                .filter(challenge -> challenge.getLastData().isAfter(serverLastsaved) || challenge.getLastData().isEqual(serverLastsaved))
                .collect(Collectors.toList());
    }

    //가장 최신 데이터 이후 수정된 데이터는 나중에...
    /*public List<ChallengeRequestdto> filterModifiedAfter(List<ChallengeRequestdto> localChallenges, LocalDateTime serverLastModified) {
        return localChallenges.stream()
                .filter(challenge -> challenge.getLastData().isAfter(serverLastModified))
                .collect(Collectors.toList());
    }*/
}
