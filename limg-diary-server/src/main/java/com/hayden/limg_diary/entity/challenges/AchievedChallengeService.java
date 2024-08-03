package com.hayden.limg_diary.entity.challenges;

import com.hayden.limg_diary.entity.challenges.dto.GetAchievedResponseDto;
import com.hayden.limg_diary.entity.challenges.dto.GetUnachievedResponseDto;
import com.hayden.limg_diary.entity.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AchievedChallengeService {

    AchievedChallengeRepository achievedChallengeRepository;
    ChallengeRepository challengeRepository;

    // value
    @Value("${path.uri}")
    String uri;


    @Autowired
    public AchievedChallengeService(AchievedChallengeRepository achievedChallengeRepository, ChallengeRepository challengeRepository) {
        this.achievedChallengeRepository = achievedChallengeRepository;
        this.challengeRepository = challengeRepository;
    }

    public ResponseEntity<GetAchievedResponseDto> getAchievedByUser(UserEntity user) throws MalformedURLException {
        List<AchievedChallengeEntity> achievedChallenges = achievedChallengeRepository.findAllByUser(user);

        GetAchievedResponseDto responseDto = new GetAchievedResponseDto();
        responseDto.setState(HttpStatus.OK, true, "success");

        for (AchievedChallengeEntity entity:achievedChallenges){
            ChallengeEntity challenge = entity.getChallenge();

            responseDto.addData(entity.getId()
            , String.format("%s/challenge/icon/%d/%b", uri, entity.getId(), false)
            , challenge.getName()
            , challenge.getSpecific()
            , entity.getCreatedData());
        }

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    public ResponseEntity<GetUnachievedResponseDto> getUnachievedByUser(UserEntity user) throws MalformedURLException {

        // find unachieved
        List<ChallengeEntity> unachievedChallenges = challengeRepository.findAll();
        List<AchievedChallengeEntity> achievedChallenges = achievedChallengeRepository.findAllByUser(user);

        for (AchievedChallengeEntity achieved:achievedChallenges){
            unachievedChallenges.remove(achieved.getChallenge());
        }

        GetUnachievedResponseDto responseDto = new GetUnachievedResponseDto();
        responseDto.setState(HttpStatus.OK, true, "success");

        for (ChallengeEntity entity: unachievedChallenges){

            responseDto.addData(
                    entity.getId()
                    , String.format("%s/challenge/icon/%d/%b", uri, entity.getId(), false)
                    , entity.getName()
                    , entity.getSpecific()
            );
        }

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
