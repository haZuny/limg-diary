package com.hayden.limg_diary.entity.challenges;

import com.hayden.limg_diary.entity.challenges.dto.GetByIdResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/challenge")
public class ChallengeContorller {
    ChallengeService challengeService;

    @Autowired
    public ChallengeContorller(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @GetMapping("/{challengeId}")
    public ResponseEntity<GetByIdResponseDto> getById(@PathVariable int challengeId){
        return challengeService.getById(challengeId);
    }
}
