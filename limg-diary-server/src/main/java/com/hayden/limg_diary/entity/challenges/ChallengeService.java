package com.hayden.limg_diary.entity.challenges;

import com.hayden.limg_diary.entity.challenges.dto.GetByIdResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChallengeService {
    ChallengeRepository challengeRepository;

    @Autowired
    public ChallengeService(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    public ResponseEntity<GetByIdResponseDto> getById(int id){
        Optional<ChallengeEntity> challengeOptiaonl = challengeRepository.findById(id);

        GetByIdResponseDto getByIdResponseDto = new GetByIdResponseDto();

        // get success
        if (challengeOptiaonl.isPresent()){
            ChallengeEntity challenge = challengeOptiaonl.get();
            getByIdResponseDto.setStatus(HttpStatus.OK, true, "success");
            getByIdResponseDto.getData().setDataValue(
                    challenge.getId()
                    , challenge.getName()
                    , challenge.getSpecific()
                    , challenge.getAchievedIconPath()
                    , challenge.getUnachievedIconPath());
            return new ResponseEntity<>(getByIdResponseDto, HttpStatus.OK);
        }

        // get fail
        getByIdResponseDto.setStatus(HttpStatus.BAD_REQUEST, false, "challenge id not found");
        getByIdResponseDto.setData(null);
        return new ResponseEntity<>(getByIdResponseDto, HttpStatus.BAD_GATEWAY);
    }
}
