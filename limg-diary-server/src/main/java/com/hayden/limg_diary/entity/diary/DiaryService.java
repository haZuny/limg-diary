package com.hayden.limg_diary.entity.diary;

import com.hayden.limg_diary.entity.diary.dto.*;
import com.hayden.limg_diary.entity.hashtag.DiaryAndHashtagEntity;
import com.hayden.limg_diary.entity.hashtag.DiaryAndHashtagRepository;
import com.hayden.limg_diary.entity.hashtag.DiaryAndHashtagService;
import com.hayden.limg_diary.entity.hashtag.HashtagEntity;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
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
        }
        if (idDiary.getUser().getId() != userEntity.getId()) {
            diaryIdResponseDto.setState(HttpStatus.UNAUTHORIZED, false, "user not authenticatied");
            diaryIdResponseDto.setData(null);
            return new ResponseEntity<>(diaryIdResponseDto, HttpStatus.UNAUTHORIZED);
        }
        ArrayList<DiaryAndHashtagEntity> hashtagById = diaryAndHashtagRepository.findAllByDiary(idDiary);
        ArrayList<String> hashtag = new ArrayList<>();
        int index = 0;
        while(index < hashtagById.size()){
            hashtag.add(hashtagById.get(index).getHashtag().getTag());
            ++index;
        }
        diaryIdResponseDto.setState(HttpStatus.OK, true, "success");
        diaryIdResponseDto.getData().setDataValue(idDiary.getId(), idDiary.getContent(), null, idDiary.getCreatedData(), idDiary.getUpdatedData(), hashtag);
        return new ResponseEntity<>(diaryIdResponseDto, HttpStatus.OK);
    }

    public ResponseEntity<DiaryMonthResponseDto> diaryMonth(int year, int month, CustomUserDetails user) {
        DiaryTodayResponseDto diaryTodayResponseDto = new DiaryTodayResponseDto();
        DiaryMonthResponseDto diaryMonthResponseDto = new DiaryMonthResponseDto();
        UserEntity userEntity = user.getUserEntity();
        //유저의 id와 일치하는 다이어리를 생성날짜순으로 가져옴
        List<DiaryEntity> diaryList = diaryRepository.findAllByUserOrderByCreatedDataDesc(userEntity);
        if (diaryList.size() > 0) {
            int index = 0;
            while(index < diaryList.size()){
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(diaryList.get(index).getCreatedData());
                if(calendar.get(Calendar.YEAR) == year &&
                        calendar.get(Calendar.MONTH)+1 == month){
                    diaryTodayResponseDto.getData().setDataValue(diaryList.get(index).getId(),null,diaryList.get(index).getCreatedData());
                    diaryTodayResponseDto.setState(HttpStatus.OK, true, "success");
                    diaryMonthResponseDto.getDataList().add(diaryTodayResponseDto);
                }
                ++index;
            }
        }
        diaryMonthResponseDto.setState(HttpStatus.OK, true, "success");
        return new ResponseEntity<>(diaryMonthResponseDto, HttpStatus.OK);
    }

    public ResponseEntity<DiaryRequestResponseDto> diaryRequest(Date sdate, Date edate, String keyword, String align, CustomUserDetails user) {
        DiaryTodayResponseDto diaryTodayResponseDto = new DiaryTodayResponseDto();
        DiaryRequestResponseDto diaryRequestResponseDto = new DiaryRequestResponseDto();
        UserEntity userEntity = user.getUserEntity();
        //유저의 id와 일치하는 다이어리를 생성날짜순으로 가져옴
        List<DiaryEntity> diaryList = diaryRepository.findAllByUserOrderByCreatedDataDesc(userEntity);
        if (diaryList.size() > 0) {
            int index = 0;
            while(index < diaryList.size()){
                Calendar scalendar = Calendar.getInstance();
                Calendar ecalendar = Calendar.getInstance();
                scalendar.setTime(sdate);
                ecalendar.setTime(edate);
                if(scalendar.get(Calendar.MONTH) >11 || scalendar.get(Calendar.MONTH) < 0
                || ecalendar.get(Calendar.MONTH) >11 || ecalendar.get(Calendar.MONTH) < 0){
                    diaryRequestResponseDto.setDataList(null);
                    return new ResponseEntity<>(diaryRequestResponseDto, HttpStatus.BAD_REQUEST);
                }
                if(scalendar.get(Calendar.MONTH) == null)

            }
        }
        diaryRequestResponseDto.setState(HttpStatus.OK, true, "success");
        return new ResponseEntity<>(diaryRequestResponseDto, HttpStatus.OK);
    }
}