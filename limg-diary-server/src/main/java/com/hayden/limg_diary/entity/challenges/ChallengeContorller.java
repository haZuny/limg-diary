package com.hayden.limg_diary.entity.challenges;

import com.hayden.limg_diary.entity.challenges.dto.GetAchievedResponseDto;
import com.hayden.limg_diary.entity.challenges.dto.GetByIdResponseDto;
import com.hayden.limg_diary.entity.challenges.dto.GetUnachievedResponseDto;
import com.hayden.limg_diary.entity.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

@RestController
@RequestMapping("/challenge")
public class ChallengeContorller {
    ChallengeService challengeService;
    AchievedChallengeService achievedChallengeService;

    @Autowired
    public ChallengeContorller(ChallengeService challengeService, AchievedChallengeService achievedChallengeService) {
        this.challengeService = challengeService;
        this.achievedChallengeService = achievedChallengeService;
    }

    @GetMapping("/{challengeId}")
    public ResponseEntity<GetByIdResponseDto> getById(@PathVariable int challengeId){
        return challengeService.getById(challengeId);
    }

    @GetMapping("/achieved")
    public ResponseEntity<GetAchievedResponseDto> getAchieved(@AuthenticationPrincipal CustomUserDetails customUser){
        return achievedChallengeService.getAchievedByUser(customUser.getUserEntity());
    }

    @GetMapping("/unachieved")
    public ResponseEntity<GetUnachievedResponseDto> getUnachieved(@AuthenticationPrincipal CustomUserDetails customUser){
        return achievedChallengeService.getUnachievedByUser(customUser.getUserEntity());
    }
}
