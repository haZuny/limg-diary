package com.hayden.limg_diary.entity.hashtag;

import com.hayden.limg_diary.entity.diary.DiaryEntity;
import com.hayden.limg_diary.entity.diary.DiaryRepository;
import com.hayden.limg_diary.entity.hashtag.dto.GetDiaryByTagResponseDto;
import com.hayden.limg_diary.entity.user.CustomUserDetails;
import com.hayden.limg_diary.entity.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DiaryAndHashtagService {
    DiaryAndHashtagRepository diaryAndHashtagRepository;
    HashtagRepository hashtagRepository;
    DiaryRepository diaryRepository;

    @Autowired
    public DiaryAndHashtagService(DiaryAndHashtagRepository diaryAndHashtagRepository, HashtagRepository hashtagRepository, DiaryRepository diaryRepository) {
        this.diaryAndHashtagRepository = diaryAndHashtagRepository;
        this.hashtagRepository = hashtagRepository;
        this.diaryRepository = diaryRepository;
    }

    // 일기 작성시 태그 추가
    public boolean addDiaryAndTag(DiaryEntity diaryEntity, String tag) {
        // get Hashtag Entity
        HashtagEntity hashtagEntity = hashtagRepository.findByTag(tag);
        if (hashtagEntity == null) {
            HashtagEntity newHashtagEntity = new HashtagEntity();
            newHashtagEntity.setTag(tag);
            newHashtagEntity.setDiary_cnt(1);
            hashtagEntity = hashtagRepository.save(newHashtagEntity);
        } else {
            hashtagEntity.setDiary_cnt(hashtagEntity.getDiary_cnt() + 1);
            hashtagEntity = hashtagRepository.save(hashtagEntity);
        }

        // add DiaryAndHashtag
        DiaryAndHashtagEntity diaryAndHashtagEntity = new DiaryAndHashtagEntity();
        diaryAndHashtagEntity.setHashtag(hashtagEntity);
        diaryAndHashtagEntity.setDiary(diaryEntity);
        diaryAndHashtagRepository.save(diaryAndHashtagEntity);
        return true;
    }

    public boolean deleteAllTagOfDiary(DiaryEntity diary) {
        ArrayList<DiaryAndHashtagEntity> diaryAndHashtagEntities = diaryAndHashtagRepository.findAllByDiary(diary);

        for (DiaryAndHashtagEntity diaryAndHashtag : diaryAndHashtagEntities) {
            // get Hashtag
            HashtagEntity hashtagEntity = diaryAndHashtag.getHashtag();

            // delete diaryAndHashtag
            diaryAndHashtagRepository.delete(diaryAndHashtag);

            // set tag cnt
            if (hashtagEntity.getDiary_cnt() > 1) {
                hashtagEntity.setDiary_cnt(hashtagEntity.getDiary_cnt() - 1);
                hashtagEntity = hashtagRepository.save(hashtagEntity);
            } else {
                hashtagRepository.delete(hashtagEntity);
            }
        }
        return true;
    }


    public ResponseEntity<GetDiaryByTagResponseDto> getDiaryByTag(ArrayList<String> requestDto, CustomUserDetails userDetails) {

        /**
         * 1. user가 작성한 모든 diary_id get (diary_repo)
         * 2. 각 다이어리가 가지고 있는 HashtagEntity 들의 list를 get (DiaryAndHashtag_repo_findAllByDiary)
         * 3. 그렇게 얻은 리스트에, reqDto의 태그들이 모드 포함된 경우만 다이어리 정보 추가
         */

        // response dto
        GetDiaryByTagResponseDto responseDto = new GetDiaryByTagResponseDto();

        // request null check
        if (requestDto == null) {
            responseDto.setState(HttpStatus.BAD_REQUEST, false, "required parameters not received");
            responseDto.setData(null);
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }

        // get user entity
        UserEntity user = userDetails.getUserEntity();

        // get tag entity
        ArrayList<HashtagEntity> tags = new ArrayList<>();
        for (String tag : requestDto) {
            tags.add(hashtagRepository.findByTag(tag));
        }

        // 01. diary by user
        ArrayList<DiaryEntity> diaryList = diaryRepository.findAllByUserOrderByCreatedDateDesc(user);

        // 02. get Hashtag list by diaryid
        for (DiaryEntity diary : diaryList) {
            ArrayList<DiaryAndHashtagEntity> diaryAndHashtagList = diaryAndHashtagRepository.findAllByDiary(diary);

            // 03. check diary contains all tags
            boolean containAllTag = true;
            for (HashtagEntity hashtag : tags) {

                // 03_2. check one tag is contained
                boolean tagContained = false;
                for (DiaryAndHashtagEntity diaryAndHashtag : diaryAndHashtagList) {
                    if (diaryAndHashtag.getHashtag() == hashtag) {
                        tagContained = true;
                        break;
                    }
                }

                // 03_2. if tag isn't container, stop checking
                if (!tagContained) {
                    containAllTag = false;
                    break;
                }
            }

            // 03_3. if all tag is container, add diary to response
            if (containAllTag)
                responseDto.addData(diary.getId(), "1234");
        }


        // return response
        responseDto.setState(HttpStatus.OK, true, "success");
        return new ResponseEntity<GetDiaryByTagResponseDto>(responseDto, HttpStatus.OK);
    }
}
