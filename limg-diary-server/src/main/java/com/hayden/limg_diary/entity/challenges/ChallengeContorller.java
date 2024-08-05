package com.hayden.limg_diary.entity.challenges;

import com.hayden.limg_diary.entity.challenges.dto.GetAchievedResponseDto;
import com.hayden.limg_diary.entity.challenges.dto.GetByIdResponseDto;
import com.hayden.limg_diary.entity.challenges.dto.GetUnachievedResponseDto;
import com.hayden.limg_diary.entity.user.CustomUserDetails;
import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
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
    public ResponseEntity<GetAchievedResponseDto> getAchieved(@AuthenticationPrincipal CustomUserDetails customUser) throws MalformedURLException {
        return achievedChallengeService.getAchievedByUser(customUser.getUserEntity());
    }

    @GetMapping("/unachieved")
    public ResponseEntity<GetUnachievedResponseDto> getUnachieved(@AuthenticationPrincipal CustomUserDetails customUser) throws MalformedURLException {
        return achievedChallengeService.getUnachievedByUser(customUser.getUserEntity());
    }

    // image icon
    @GetMapping(value = "/icon/{challenge_id}/{achieved}", produces = MediaType.IMAGE_PNG_VALUE)
    public Resource getChallengeIcon(@PathVariable int challenge_id, @PathVariable boolean achieved) throws MalformedURLException {
        return challengeService.getChallengeIcon(challenge_id, achieved);
    }
}
