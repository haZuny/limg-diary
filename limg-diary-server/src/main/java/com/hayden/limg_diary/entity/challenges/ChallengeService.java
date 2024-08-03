package com.hayden.limg_diary.entity.challenges;

import com.hayden.limg_diary.entity.challenges.dto.GetByIdResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.Optional;

@Service
public class ChallengeService {
    ChallengeRepository challengeRepository;

    @Value("${path.uri}")
    String uri;
    @Value("${path.resources}")
    String resPath;

    @Autowired
    public ChallengeService(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    // get Challenge Specific
    public ResponseEntity<GetByIdResponseDto> getById(int id){
        Optional<ChallengeEntity> challengeOptiaonl = challengeRepository.findById(id);

        GetByIdResponseDto getByIdResponseDto = new GetByIdResponseDto();

        // get success
        if (challengeOptiaonl.isPresent()){
            ChallengeEntity challenge = challengeOptiaonl.get();
            getByIdResponseDto.setState(HttpStatus.OK, true, "success");
            getByIdResponseDto.getData().setDataValue(
                    challenge.getId()
                    , challenge.getName()
                    , challenge.getSpecific()
                    , String.format("%s/challenge/icon/%d/true", uri, challenge.getId())
                    , String.format("%s/challenge/icon/%d/false", uri, challenge.getId())
            );
            return new ResponseEntity<>(getByIdResponseDto, HttpStatus.OK);
        }

        // get fail
        getByIdResponseDto.setState(HttpStatus.BAD_REQUEST, false, "challenge id not found");
        getByIdResponseDto.setData(null);
        return new ResponseEntity<>(getByIdResponseDto, HttpStatus.BAD_GATEWAY);
    }


    // Get chllenge icon
    public Resource getChallengeIcon(int challengeId, boolean achieved) throws MalformedURLException {
        Optional<ChallengeEntity> challengeOptional = challengeRepository.findById(challengeId);
        // null check
        if (challengeOptional.isEmpty())
            return null;
        if (achieved)
            return new UrlResource(String.format("file:%s%s", resPath, challengeOptional.get().getAchievedIconPath()));
        else
            return new UrlResource(String.format("file:%s%s", resPath, challengeOptional.get().getUnachievedIconPath()));
    }
}
