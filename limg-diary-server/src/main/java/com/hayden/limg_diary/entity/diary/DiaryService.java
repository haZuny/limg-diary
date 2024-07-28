package com.hayden.limg_diary.entity.diary;

import com.hayden.limg_diary.entity.diary.dto.DiaryAddRequestDto;
import com.hayden.limg_diary.entity.diary.dto.DiaryIdResponseDto;
import com.hayden.limg_diary.entity.diary.dto.DiaryTodayResponseDto;
import com.hayden.limg_diary.entity.hashtag.DiaryAndHashtagRepository;
import com.hayden.limg_diary.entity.hashtag.DiaryAndHashtagService;
import com.hayden.limg_diary.entity.today_rate.DiaryAndTodayRateService;
import com.hayden.limg_diary.entity.today_rate.TodayRateEntity;
import com.hayden.limg_diary.entity.today_rate.TodayRateRepository;
import com.hayden.limg_diary.entity.user.CustomUserDetails;
import com.hayden.limg_diary.entity.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DiaryService {
    DiaryRepository diaryRepository;
    DiaryAndTodayRateService diaryAndTodayRateService;
    DiaryAndHashtagService diaryAndHashtagService;
    DiaryAndHashtagRepository diaryAndHashtagRepository;

    @Autowired
    public DiaryService(DiaryAndHashtagRepository diaryAndHashtagRepository, DiaryAndHashtagService diaryAndHashtagService, DiaryRepository diaryRepository, DiaryAndTodayRateService diaryAndTodayRateService, TodayRateRepository todayRateRepository) {
        this.diaryRepository = diaryRepository;
        this.diaryAndTodayRateService = diaryAndTodayRateService;
        this.diaryAndHashtagService = diaryAndHashtagService;
        this.diaryAndHashtagRepository = diaryAndHashtagRepository;
    }

    public boolean diaryAdd(DiaryAddRequestDto diaryAddRequestDto, CustomUserDetails user) {
        if (diaryAddRequestDto.getContent() == null) return false;
        if (diaryAddRequestDto.getDraw_style() == null) return false;

        //다이어리 엔티티에 컨텐츠 저장
        DiaryEntity diaryEntity = new DiaryEntity();
        diaryEntity.setContent(diaryAddRequestDto.getContent());
        diaryEntity.setUser(user.getUserEntity());
        diaryRepository.save(diaryEntity);

        //하루 평가 저장
        diaryAndTodayRateService.DiaryAndTodayRateAdd(diaryEntity, diaryAddRequestDto.getToday_rate());

        //해시태그 저장
        diaryAndHashtagService.DiaryAndHashtagAdd(diaryEntity, diaryAddRequestDto.getHashtag());

        return true;
    }

    public ResponseEntity<DiaryTodayResponseDto> diaryToday(CustomUserDetails user) {
        DiaryTodayResponseDto diaryTodayResponseDto = new DiaryTodayResponseDto();
        UserEntity userEntity = user.getUserEntity();
        //유저의 id와 일치하는 다이어리를 생성날짜순으로 가져옴
        List<DiaryEntity> diaryList = diaryRepository.findAllByUserOrderByCreatedDataDesc(userEntity);
        DiaryEntity todayDiary;
        if (diaryList.size() > 0) {
            todayDiary = diaryList.get(0);

            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (dateFormat.format(todayDiary.getCreatedData()).equals(dateFormat.format(now))) {
                diaryTodayResponseDto.setState(HttpStatus.OK, true, "success");
                diaryTodayResponseDto.getData().setDataValue(todayDiary.getId(), null, todayDiary.getCreatedData());
                return new ResponseEntity<>(diaryTodayResponseDto, HttpStatus.OK);
            }
        }
        diaryTodayResponseDto.setState(HttpStatus.NOT_FOUND, false, "fail");
        diaryTodayResponseDto.setData(null);
        return new ResponseEntity<>(diaryTodayResponseDto, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<DiaryIdResponseDto> diaryId(int diaryId, CustomUserDetails user) {
        DiaryIdResponseDto diaryIdResponseDto = new DiaryIdResponseDto();
        UserEntity userEntity = user.getUserEntity();
        //유저의 id와 일치하는 다이어리를 생성날짜순으로 가져옴
        DiaryEntity idDiary = diaryRepository.findById(diaryId);
        if (idDiary == null) {
            diaryIdResponseDto.setState(HttpStatus.NOT_FOUND, false, "fail");
            diaryIdResponseDto.setData(null);
            return new ResponseEntity<>(diaryIdResponseDto, HttpStatus.BAD_REQUEST);
        } else if (idDiary.getUser() != userEntity) {
            diaryIdResponseDto.setState(HttpStatus.UNAUTHORIZED, false, "fail");
            diaryIdResponseDto.setData(null);
            return new ResponseEntity<>(diaryIdResponseDto, HttpStatus.UNAUTHORIZED);
        }
        diaryAndHashtagRepository.findByDiary(idDiary);
        diaryIdResponseDto.setState(HttpStatus.OK, true, "success");
        diaryIdResponseDto.getData().setDataValue(idDiary.getId(), idDiary.getContent(), null, idDiary.getCreatedData(), idDiary.getUpdatedData(),);
    }
}
