package com.hayden.limg_diary.entity.hashtag;

import com.hayden.limg_diary.entity.diary.DiaryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DiaryAndHashtagService {
    DiaryAndHashtagRepository diaryAndHashtagRepository;
    HashtagRepository hashtagRepository;

    @Autowired
    public DiaryAndHashtagService(DiaryAndHashtagRepository diaryAndHashtagRepository, HashtagRepository hashtagRepository) {
        this.diaryAndHashtagRepository = diaryAndHashtagRepository;
        this.hashtagRepository = hashtagRepository;
    }
    public boolean DiaryAndHashtagAdd(DiaryEntity diaryEntity, ArrayList<String> tag){
        int index = 0;
        while(index< tag.size()){

            DiaryAndHashtagEntity diaryAndHashtagEntity = new DiaryAndHashtagEntity();

            HashtagEntity hashtagEntity = hashtagRepository.findByTag(tag.get(index));

            if(hashtagEntity == null) {
                HashtagEntity newHashtagEntity = new HashtagEntity();
                newHashtagEntity.setTag(tag.get(index));
                newHashtagEntity.setDiary_cnt(1);
                hashtagEntity = hashtagRepository.save(newHashtagEntity);
            }
            else{
                hashtagEntity.setDiary_cnt(hashtagEntity.getDiary_cnt() + 1);
                hashtagRepository.save(hashtagEntity);
            }
            ++index;
            diaryAndHashtagEntity.setHashtag(hashtagEntity);


            diaryAndHashtagEntity.setDiary(diaryEntity);
            diaryAndHashtagRepository.save(diaryAndHashtagEntity);
        }
        return true;
    }

}
