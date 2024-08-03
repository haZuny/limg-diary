package com.hayden.limg_diary.entity.diary;

import com.hayden.limg_diary.entity.DefaultResponseDto;
import com.hayden.limg_diary.entity.diary.dto.*;
import com.hayden.limg_diary.entity.draw_style.DrawStyleEntity;
import com.hayden.limg_diary.entity.draw_style.DrawStyleRepository;
import com.hayden.limg_diary.entity.hashtag.DiaryAndHashtagEntity;
import com.hayden.limg_diary.entity.hashtag.DiaryAndHashtagRepository;
import com.hayden.limg_diary.entity.hashtag.DiaryAndHashtagService;
import com.hayden.limg_diary.entity.picture.PictureEntity;
import com.hayden.limg_diary.entity.picture.PictureRepository;
import com.hayden.limg_diary.entity.picture.PictureService;
import com.hayden.limg_diary.entity.today_rate.TodayRateEntity;
import com.hayden.limg_diary.entity.today_rate.TodayRateRepository;
import com.hayden.limg_diary.entity.user.CustomUserDetails;
import com.hayden.limg_diary.entity.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DiaryService {
    DiaryRepository diaryRepository;
    DiaryAndHashtagService diaryAndHashtagService;
    TodayRateRepository todayRateRepository;
    DiaryAndHashtagRepository diaryAndHashtagRepository;

    DrawStyleRepository drawStyleRepository;

    PictureRepository pictureRepository;
    PictureService pictureService;

    @Value("${path.resources}")
    String resPath;

    @Value("${path.uri}")
    String uriPath;

    @Autowired
    public DiaryService(DiaryAndHashtagRepository diaryAndHashtagRepository,
                        DiaryAndHashtagService diaryAndHashtagService,
                        TodayRateRepository todayRateRepository,
                        DiaryRepository diaryRepository,
                        DrawStyleRepository drawStyleRepository,
                        PictureService pictureService,
                        PictureRepository pictureRepository) {
        this.diaryRepository = diaryRepository;
        this.diaryAndHashtagService = diaryAndHashtagService;
        this.todayRateRepository = todayRateRepository;
        this.diaryAndHashtagRepository = diaryAndHashtagRepository;
        this.drawStyleRepository = drawStyleRepository;
        this.pictureRepository = pictureRepository;
        this.pictureService = pictureService;
    }

    public Resource getDiaryImage(int diaryId, CustomUserDetails userDetails) throws MalformedURLException {

        UserEntity userEntity = userDetails.getUserEntity();

        DiaryEntity diary = diaryRepository.findById(diaryId);

        // diary null check
        if (diary == null)  throw new NoSuchFieldError("diary not found");
        // user check
        if (diary.getUser().getId() != userEntity.getId())  throw new IllegalAccessError("user not match with diary");

        PictureEntity picture = pictureRepository.findByDiary(diary);

        // picture null check
        if (picture == null) return null;

        // picture path null check
        if (picture.getPath() != null)
            return new UrlResource(String.format("file:%s%s", resPath, picture.getPath()));
        return null;
    }

    public ResponseEntity<DefaultResponseDto> diaryAdd(DiaryAddRequestDto diaryAddRequestDto, CustomUserDetails user) {
        DefaultResponseDto responseDto = new DefaultResponseDto();

        // get drawStyle Entity
        Optional<DrawStyleEntity> drawStyleOptional = drawStyleRepository.findByStyleEng(diaryAddRequestDto.getDraw_style());
        if (drawStyleOptional.isEmpty()){
            responseDto.setState(HttpStatus.BAD_REQUEST, false, "diary drawstyle is empty");
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }

        // content null check
        if (diaryAddRequestDto.getContent() == null) {
            responseDto.setState(HttpStatus.BAD_REQUEST, false, "diary content is empty");
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }

        // get todayRate Entity
        TodayRateEntity todayRateEntity = todayRateRepository.findByRateNum(diaryAddRequestDto.getToday_rate());
        if (todayRateEntity == null){
            responseDto.setState(HttpStatus.BAD_REQUEST, false, "today rate is not match");
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }

        //다이어리 엔티티에 컨텐츠 저장
        DiaryEntity diaryEntity = new DiaryEntity();
        diaryEntity.setContent(diaryAddRequestDto.getContent());
        diaryEntity.setUser(user.getUserEntity());
        diaryEntity.setTodayRate(todayRateEntity);
        diaryEntity = diaryRepository.save(diaryEntity);


        //해시태그 저장
        diaryAndHashtagService.DiaryAndHashtagAdd(diaryEntity, diaryAddRequestDto.getHashtag());

        // 그림 생성 및 저장
        pictureService.createPicture(diaryEntity, drawStyleOptional.get());

        // return
        responseDto.setState(HttpStatus.OK, true, "success");
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    public ResponseEntity<DiaryTodayResponseDto> diaryToday(CustomUserDetails user) {

        // response dto
        DiaryTodayResponseDto diaryTodayResponseDto = new DiaryTodayResponseDto();

        // get user
        UserEntity userEntity = user.getUserEntity();

        // get today diary
        Date today_s = new Date();
        Date today_e = new Date();
        today_s.setTime();



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

    // Get By Id
    public ResponseEntity<DiaryIdResponseDto> getByDiaryId(int diaryId, CustomUserDetails user) {

        // response dto
        DiaryIdResponseDto diaryIdResponseDto = new DiaryIdResponseDto();

        // find user and diary
        UserEntity userEntity = user.getUserEntity();
        DiaryEntity diaryEntity = diaryRepository.findById(diaryId);

        // diary null check
        if (diaryEntity == null) {
            diaryIdResponseDto.setState(HttpStatus.NOT_FOUND, false, "fail");
            diaryIdResponseDto.setData(null);
            return new ResponseEntity<>(diaryIdResponseDto, HttpStatus.BAD_REQUEST);
        }

        // user matching check
        if (diaryEntity.getUser().getId() != userEntity.getId()) {
            diaryIdResponseDto.setState(HttpStatus.UNAUTHORIZED, false, "user not authenticatied");
            diaryIdResponseDto.setData(null);
            return new ResponseEntity<>(diaryIdResponseDto, HttpStatus.UNAUTHORIZED);
        }

        // find picture
        PictureEntity picture = pictureRepository.findByDiary(diaryEntity);

        // find hashtag
        ArrayList<DiaryAndHashtagEntity> tagEntities = diaryAndHashtagRepository.findAllByDiary(diaryEntity);
        ArrayList<String> tags = new ArrayList<>();
        for (DiaryAndHashtagEntity tag : tagEntities)  tags.add(tag.getHashtag().getTag());


        // set response entity
        diaryIdResponseDto.setState(HttpStatus.OK, true, "success");
        diaryIdResponseDto.getData().setDiary_id(diaryEntity.getId());
        diaryIdResponseDto.getData().setContent(diaryEntity.getContent());
        if (picture.getPath() == null){
            diaryIdResponseDto.getData().setPicture(null);
        }
        else{
            diaryIdResponseDto.getData().setPicture(String.format("%s/diary/img/%d", uriPath, diaryEntity.getId()));
        }
        diaryIdResponseDto.getData().setCreated_date(diaryEntity.getCreatedData());
        diaryIdResponseDto.getData().setUpdated_date(diaryEntity.getUpdatedData());
        diaryIdResponseDto.getData().getToday_rate().setRate_num(diaryEntity.getTodayRate().getRateNum());
        diaryIdResponseDto.getData().getToday_rate().setRate_str(diaryEntity.getTodayRate().getRateStr());
        diaryIdResponseDto.getData().setHashtag(tags);
        diaryIdResponseDto.getData().getDraw_style().setStyle_eng(picture.getDrawStyle().getStyleEng());
        diaryIdResponseDto.getData().getDraw_style().setStyle_kor(picture.getDrawStyle().getStyleKor());

        // return
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

    public ResponseEntity<DiaryRequestResponseDto> diaryRequest(String sdate, String edate, String keyword, String align, CustomUserDetails user) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        DiaryTodayResponseDto diaryTodayResponseDto = new DiaryTodayResponseDto();
        DiaryRequestResponseDto diaryRequestResponseDto = new DiaryRequestResponseDto();
        UserEntity userEntity = user.getUserEntity();
        //유저의 id와 일치하는 다이어리를 생성날짜순으로 가져옴
        ArrayList<DiaryEntity> diaryList;
        if(align == null || align.equals("recent")) {
            diaryList = diaryRepository.findAllByUserOrderByCreatedDataDesc(userEntity);
        }
        else {
            diaryList = diaryRepository.findAllByUserOrderByCreatedDataAsc(userEntity);
        }
        if (diaryList.size() > 0) {
            if(keyword != null) {
                int index = 0;
                ArrayList<DiaryEntity> tempList = new ArrayList<>();
                while (index < diaryList.size()) {
                    if(diaryList.get(index).getContent().contains(keyword)){
                        tempList.add(diaryList.get(index));
                        System.out.println(keyword);
                    }
                    ++index;
                }
                diaryList = tempList;
                System.out.println(keyword);
            }

            if(sdate != null){
                Date sDate = simpleDateFormat.parse(sdate);
                int index = 0;
                ArrayList<DiaryEntity> tempList = new ArrayList<>();
                if(align == null || align.equals("recent")){
                    while(index < diaryList.size()){
                        if (sDate.compareTo(diaryList.get(index).getCreatedData()) < 1) {
                            tempList.add(diaryList.get(index));
                        }
                        ++index;
                    }
                    diaryList = tempList;
                }
                else{
                    Collections.reverse(diaryList);
                    while(index < diaryList.size()){
                        if (sDate.compareTo(diaryList.get(index).getCreatedData()) < 1) {
                            tempList.add(diaryList.get(index));
                        }
                        ++index;
                    }
                    Collections.reverse(tempList);
                    diaryList = tempList;
                }
            }

            if(edate != null){
                Date eDate = simpleDateFormat.parse(edate);
                Calendar cal = Calendar.getInstance();
                cal.setTime(eDate);
                cal.set(Calendar.HOUR_OF_DAY, 23);
                cal.set(Calendar.MINUTE, 59);
                cal.set(Calendar.SECOND, 59);
                cal.set(Calendar.MILLISECOND, 999);
                eDate = cal.getTime();

                int index = 0;
                ArrayList<DiaryEntity> tempList = new ArrayList<>();
                if(align == null || align.equals("recent")){
                    Collections.reverse(diaryList);
                    while(index < diaryList.size()){
                        System.out.println(eDate.compareTo(diaryList.get(index).getCreatedData()));
                        if (eDate.compareTo(diaryList.get(index).getCreatedData()) > -1) {
                            tempList.add(diaryList.get(index));
                        }
                        ++index;
                    }
                    Collections.reverse(tempList);
                    diaryList = tempList;
                }
                else{
                    while(index < diaryList.size()){
                        if (eDate.compareTo(diaryList.get(index).getCreatedData()) > -1) {
                            tempList.add(diaryList.get(index));
                        }
                        ++index;
                    }
                    diaryList = tempList;
                }
            }
        }
        int index = 0;
        while(index < diaryList.size()){
            DiaryTodayResponseDto diaryTodayResponseDto2 = new DiaryTodayResponseDto();
            diaryTodayResponseDto2.getData().setDataValue(diaryList.get(index).getId(), null, diaryList.get(index).getCreatedData());
            diaryTodayResponseDto2.setState(HttpStatus.OK, true, "success");
            diaryRequestResponseDto.getDataList().add(diaryTodayResponseDto2);
            ++index;
        }
        diaryRequestResponseDto.setState(HttpStatus.OK, true, "success");
        return new ResponseEntity<>(diaryRequestResponseDto, HttpStatus.OK);
    }
}