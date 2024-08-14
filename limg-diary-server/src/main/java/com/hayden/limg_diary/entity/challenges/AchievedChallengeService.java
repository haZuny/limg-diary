package com.hayden.limg_diary.entity.challenges;

import com.hayden.limg_diary.entity.challenges.dto.GetAchievedResponseDto;
import com.hayden.limg_diary.entity.challenges.dto.GetUnachievedResponseDto;
import com.hayden.limg_diary.entity.diary.DiaryEntity;
import com.hayden.limg_diary.entity.diary.DiaryRepository;
import com.hayden.limg_diary.entity.picture.PictureEntity;
import com.hayden.limg_diary.entity.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AchievedChallengeService {

    AchievedChallengeRepository achievedChallengeRepository;
    ChallengeRepository challengeRepository;
    DiaryRepository diaryRepository;

    // value
    @Value("${path.uri}")
    String uri;


    @Autowired
    public AchievedChallengeService(
            AchievedChallengeRepository achievedChallengeRepository
            , ChallengeRepository challengeRepository
            , DiaryRepository diaryRepository) {
        this.achievedChallengeRepository = achievedChallengeRepository;
        this.challengeRepository = challengeRepository;
        this.diaryRepository = diaryRepository;
    }

    public ResponseEntity<GetAchievedResponseDto> getAchievedByUser(UserEntity user) throws MalformedURLException {
        List<AchievedChallengeEntity> achievedChallenges = achievedChallengeRepository.findAllByUser(user);

        GetAchievedResponseDto responseDto = new GetAchievedResponseDto();

        for (AchievedChallengeEntity entity:achievedChallenges){
            ChallengeEntity challenge = entity.getChallenge();

            responseDto.addData(
                    entity.getId()
                    , String.format("/challenge/icon/%d/%b", challenge.getId(), true)
                    , challenge.getName()
                    , challenge.getDetail()
                    , entity.getCreatedDate());
        }

        responseDto.setState(HttpStatus.OK, true, "success");
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
                    , String.format("/challenge/icon/%d/%b", entity.getId(), false)
                    , entity.getName()
                    , entity.getDetail()
            );
        }

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    public boolean checkChallengeContinuous(UserEntity user){
        // find challenge entity
        Optional<ChallengeEntity> challengeOptional_1 = challengeRepository.findById(1);
        Optional<ChallengeEntity> challengeOptional_2 = challengeRepository.findById(2);
        Optional<ChallengeEntity> challengeOptional_3 = challengeRepository.findById(3);

        // find unachieved
        Optional<AchievedChallengeEntity> achievedChallengeOptional_1 = achievedChallengeRepository.findByUserAndChallenge(user, challengeOptional_1.get());
        Optional<AchievedChallengeEntity> achievedChallengeOptional_2 = achievedChallengeRepository.findByUserAndChallenge(user, challengeOptional_2.get());
        Optional<AchievedChallengeEntity> achievedChallengeOptional_3 = achievedChallengeRepository.findByUserAndChallenge(user, challengeOptional_3.get());

        // check continuous
        if (achievedChallengeOptional_1.isEmpty() || achievedChallengeOptional_2.isEmpty() || achievedChallengeOptional_3.isEmpty()){
            int continuousNum = 0;
            LocalDate lastDate = null;
            ArrayList<DiaryEntity> diaryEntities = diaryRepository.findAllByUserOrderByCreatedDateDesc(user);
            for (DiaryEntity diary : diaryEntities){
                if (lastDate != null && diary.getCreatedDate().until(lastDate).getDays() == 1)  continuousNum += 1;
                else    continuousNum = 1;

                System.out.println(continuousNum);
                // check 10
                if (achievedChallengeOptional_1.isEmpty() && continuousNum >= 10){
                    AchievedChallengeEntity achievedChallengeEntity = new AchievedChallengeEntity();
                    achievedChallengeEntity.setChallenge(challengeOptional_1.get());
                    achievedChallengeEntity.setUser(user);
                    achievedChallengeRepository.save(achievedChallengeEntity);
                    achievedChallengeOptional_1 = achievedChallengeRepository.findByUserAndChallenge(user, challengeOptional_1.get());
                }
                // check 20
                if (achievedChallengeOptional_2.isEmpty() && continuousNum >= 20){
                    AchievedChallengeEntity achievedChallengeEntity = new AchievedChallengeEntity();
                    achievedChallengeEntity.setChallenge(challengeOptional_2.get());
                    achievedChallengeEntity.setUser(user);
                    achievedChallengeRepository.save(achievedChallengeEntity);
                    achievedChallengeOptional_2 = achievedChallengeRepository.findByUserAndChallenge(user, challengeOptional_2.get());
                }
                // check 30
                if (achievedChallengeOptional_3.isEmpty() && continuousNum >= 30){
                    AchievedChallengeEntity achievedChallengeEntity = new AchievedChallengeEntity();
                    achievedChallengeEntity.setChallenge(challengeOptional_3.get());
                    achievedChallengeEntity.setUser(user);
                    achievedChallengeRepository.save(achievedChallengeEntity);
                    achievedChallengeOptional_3 = achievedChallengeRepository.findByUserAndChallenge(user, challengeOptional_3.get());
                }

                lastDate = diary.getCreatedDate();
            }
        }

        return true;
    }

    public boolean checkChallengeFullContent(DiaryEntity diary){
        // get data
        UserEntity user = diary.getUser();
        String content = diary.getContent();
        // check already achieved
        Optional<ChallengeEntity> challengeOp = challengeRepository.findById(4);
        Optional<AchievedChallengeEntity> achievedChallengeOp = achievedChallengeRepository.findByUserAndChallenge(user, challengeOp.get());

        if (achievedChallengeOp.isEmpty() && content.length() >= 1000){
            AchievedChallengeEntity achievedChallenge = new AchievedChallengeEntity();
            achievedChallenge.setUser(user);
            achievedChallenge.setChallenge(challengeOp.get());
            achievedChallengeRepository.save(achievedChallenge);
        }

        return true;
    }

    public boolean checkChallengePictureModify(PictureEntity picture){
        UserEntity user = picture.getDiary().getUser();
        int modifyCnt = picture.getModifyCount();

        Optional<ChallengeEntity> challengeOp = challengeRepository.findById(5);
        Optional<AchievedChallengeEntity> achievedChallengeOp = achievedChallengeRepository.findByUserAndChallenge(user, challengeOp.get());

        if (achievedChallengeOp.isEmpty() && modifyCnt >= 10){
            AchievedChallengeEntity achievedChallenge = new AchievedChallengeEntity();
            achievedChallenge.setUser(user);
            achievedChallenge.setChallenge(challengeOp.get());
            achievedChallengeRepository.save(achievedChallenge);
        }

        return true;
    }
}
