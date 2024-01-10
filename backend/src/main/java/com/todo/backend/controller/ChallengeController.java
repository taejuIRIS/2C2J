package com.todo.backend.controller;

import com.todo.backend.controller.request.ChallengeRequestdto;
import com.todo.backend.controller.response.ChallengeResponsedto;
import com.todo.backend.entity.ChallengeEntity;
import com.todo.backend.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
public  class ChallengeController {

    @Autowired
    private ChallengeService challengeService;

    @GetMapping
    public List<ChallengeResponsedto> getAllChallenge() {
        List<ChallengeEntity> challenge = challengeService.getAllChallenge();
        return ChallengeResponsedto.fromChallengeList(challenge);
    }

    //챌린지 추가
    @PostMapping
    public  ChallengeEntity addChallenge(@RequestBody ChallengeRequestdto challengeRequest) {
        return challengeService.addChallenge(challengeRequest);}

    //수정
    @PutMapping("/{id}")
    public ChallengeEntity updateChallenge(@PathVariable int id, @RequestBody ChallengeRequestdto challengeRequest) {
        return challengeService.updateChallenge(id, challengeRequest);
    }

    //삭제
    @DeleteMapping("/{id}")
    public void deleteChallenge(@PathVariable int id) {
        challengeService.deleteChallenge(id);
    }

}
