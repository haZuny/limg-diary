package com.hayden.limg_diary.entity.diary;

import com.hayden.limg_diary.entity.DefaultResponseDto;
import com.hayden.limg_diary.entity.diary.dto.*;
import com.hayden.limg_diary.entity.draw_style.DrawStyleEntity;
import com.hayden.limg_diary.entity.draw_style.DrawStyleRepository;
import com.hayden.limg_diary.entity.hashtag.*;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    HashtagRepository hashtagRepository;

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
                        PictureRepository pictureRepository,
                        HashtagRepository hashtagRepository) {
        this.diaryRepository = diaryRepository;
        this.hashtagRepository = hashtagRepository;
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
        if (diary == null) throw new NoSuchFieldError("diary not found");
        // user check
        if (diary.getUser().getId() != userEntity.getId()) throw new IllegalAccessError("user not match with diary");

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
        if (drawStyleOptional.isEmpty()) {
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
        if (todayRateEntity == null) {
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
        for (String tag : diaryAddRequestDto.getHashtag()){
            diaryAndHashtagService.addDiaryAndTag(diaryEntity, tag);
        }

        // 그림 생성 및 저장
        pictureService.createPicture(diaryEntity, drawStyleOptional.get());

        // return
        responseDto.setState(HttpStatus.OK, true, "success");
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    public ResponseEntity<DiaryTodayResponseDto> getDiaryByToday(CustomUserDetails user) {

        // response dto
        DiaryTodayResponseDto diaryTodayResponseDto = new DiaryTodayResponseDto();

        // get user
        UserEntity userEntity = user.getUserEntity();

        // get today diary
        LocalDate today = LocalDate.now();
        Optional<DiaryEntity> diaryEntityOptional = diaryRepository.findByCreatedDateAndUser(today, userEntity);

        // return :: null case
        if (diaryEntityOptional.isEmpty()) {
            diaryTodayResponseDto.setState(HttpStatus.OK, true, "success :: today diary is not found");
            diaryTodayResponseDto.setData(null);
            return new ResponseEntity<>(diaryTodayResponseDto, HttpStatus.OK);
        }
        DiaryEntity diary = diaryEntityOptional.get();

        // get picture
        PictureEntity picture = pictureRepository.findByDiary(diary);

        // set response dto

        diaryTodayResponseDto.getData().setDiary_id(diary.getId());
        diaryTodayResponseDto.getData().setToday(diary.getCreatedDate());
        if (picture.getPath() != null) {
//            diaryTodayResponseDto.getData().setPicture(String.format("%s/diary/img/%d", uriPath, diary.getId()));
            diaryTodayResponseDto.getData().setPicture(String.format("/diary/img/%d", diary.getId()));
        } else {
            diaryTodayResponseDto.getData().setPicture(null);
        }
        diaryTodayResponseDto.setState(HttpStatus.OK, true, "success");
        return new ResponseEntity<>(diaryTodayResponseDto, HttpStatus.OK);
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
        for (DiaryAndHashtagEntity tag : tagEntities) tags.add(tag.getHashtag().getTag());


        // set response entity
        diaryIdResponseDto.setState(HttpStatus.OK, true, "success");
        diaryIdResponseDto.getData().setDiary_id(diaryEntity.getId());
        diaryIdResponseDto.getData().setContent(diaryEntity.getContent());
        if (picture.getPath() == null) {
            diaryIdResponseDto.getData().setPicture(null);
        } else {
//            diaryIdResponseDto.getData().setPicture(String.format("%s/diary/img/%d", uriPath, diaryEntity.getId()));
            diaryIdResponseDto.getData().setPicture(String.format("/diary/img/%d", diaryEntity.getId()));
        }
        diaryIdResponseDto.getData().setCreated_date(diaryEntity.getCreatedDate());
        diaryIdResponseDto.getData().setUpdated_date(diaryEntity.getUpdatedDate());
        diaryIdResponseDto.getData().getToday_rate().setRate_num(diaryEntity.getTodayRate().getRateNum());
        diaryIdResponseDto.getData().getToday_rate().setRate_str(diaryEntity.getTodayRate().getRateStr());
        diaryIdResponseDto.getData().setHashtag(tags);
        diaryIdResponseDto.getData().getDraw_style().setStyle_eng(picture.getDrawStyle().getStyleEng());
        diaryIdResponseDto.getData().getDraw_style().setStyle_kor(picture.getDrawStyle().getStyleKor());

        // return
        return new ResponseEntity<>(diaryIdResponseDto, HttpStatus.OK);
    }

    public ResponseEntity<DiaryMonthResponseDto> getDiaryByMonth(int year, int month, CustomUserDetails user) {

        // get user
        UserEntity userEntity = user.getUserEntity();

        // find diaries
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = LocalDate.of(year, month, startDate.lengthOfMonth());
        ArrayList<DiaryEntity> diaries = diaryRepository.findAllByCreatedDateBetweenAndUserOrderByCreatedDateDesc(startDate, endDate, userEntity);

        // set response dto
        DiaryMonthResponseDto diaryMonthResponseDto = new DiaryMonthResponseDto();
        diaryMonthResponseDto.setState(HttpStatus.OK, true, "success");
        for (DiaryEntity diary : diaries){
            // find picture
            PictureEntity picture = pictureRepository.findByDiary(diary);
//            String picturePath = picture.getPath() == null ? null : String.format("%s/diary/img/%d", uriPath, diary.getId());
            String picturePath = picture.getPath() == null ? null : String.format("/diary/img/%d", diary.getId());
            diaryMonthResponseDto.addData(diary.getId(), picturePath, diary.getCreatedDate());
        }

        return new ResponseEntity<>(diaryMonthResponseDto, HttpStatus.OK);
    }

    public ResponseEntity<DiarySearchResponseDto> diarySearch(String sdate, String edate, String keyword, String align, CustomUserDetails user) throws ParseException {

        // get user
        UserEntity userEntity = user.getUserEntity();

        // get diaries by date, by recent
        ArrayList<DiaryEntity> diries;
        if (sdate == null && edate == null)
            diries = diaryRepository.findAllByUserOrderByCreatedDateDesc(userEntity);
        else if (sdate != null && edate == null){
            LocalDate srateDate = LocalDate.parse(sdate, DateTimeFormatter.ISO_DATE);
            diries = diaryRepository.findAllByCreatedDateGreaterThanEqualAndUserOrderByCreatedDateDesc(srateDate, userEntity);
        }
        else if (sdate == null && edate != null){
            LocalDate endDate = LocalDate.parse(edate, DateTimeFormatter.ISO_DATE);
            diries = diaryRepository.findAllByCreatedDateLessThanEqualAndUserOrderByCreatedDateDesc(endDate, userEntity);
        }
        else{
            LocalDate startDate = LocalDate.parse(sdate, DateTimeFormatter.ISO_DATE);
            LocalDate endDate = LocalDate.parse(edate, DateTimeFormatter.ISO_DATE);
            diries = diaryRepository.findAllByCreatedDateBetweenAndUserOrderByCreatedDateDesc(startDate, endDate, userEntity);
        }

        // filter by keyword
        if (keyword != null){
            Iterator<DiaryEntity> iterator = diries.iterator();
            while ((iterator.hasNext())){
                DiaryEntity diary = iterator.next();
                if (!diary.getContent().contains(keyword)){
                    iterator.remove();
                }
            }
        }

        // align
        if (align != null && align.equals("oldest")){
            Collections.reverse(diries);
        }

        // set response dto
        DiarySearchResponseDto diarySearchResponseDto = new DiarySearchResponseDto();
        diarySearchResponseDto.setState(HttpStatus.OK, true, "success");
        for (DiaryEntity diary : diries){
            diarySearchResponseDto.addData(diary.getId(), diary.getContent(), diary.getCreatedDate());
        }

        return new ResponseEntity<>(diarySearchResponseDto, HttpStatus.OK);
    }

    public ResponseEntity<DefaultResponseDto> diaryModify(int diaryId, DiaryModifyRequestDto diaryModifyRequestDto, CustomUserDetails user) {

        // response dto
        DefaultResponseDto responseDto = new DefaultResponseDto();

        // get diaryEntity endity
        DiaryEntity diaryEntity= diaryRepository.findById(diaryId);

        // check user
        if (diaryEntity.getUser().getId() != user.getUserEntity().getId()) {
            responseDto.setState(HttpStatus.UNAUTHORIZED, false, "user not authenticated");
            return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
        }

        // modify content
        if (diaryModifyRequestDto.getContent() != null && !diaryModifyRequestDto.getContent().isEmpty()) {
            diaryEntity.setContent(diaryModifyRequestDto.getContent());
            diaryEntity = diaryRepository.save(diaryEntity);
        }

        // modify today_rate
        if(diaryModifyRequestDto.getToday_rate() != -1){
            TodayRateEntity todayRate = todayRateRepository.findByRateNum(diaryModifyRequestDto.getToday_rate());
            if(todayRate == null){
                responseDto.setState(HttpStatus.BAD_REQUEST, false, "today rate is invalid");
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            }
            diaryEntity.setTodayRate(todayRate);
            diaryEntity = diaryRepository.save(diaryEntity);
        }

        // modify hashtag
        if (diaryModifyRequestDto.getHashtag() != null) {

            // delete all existing tags
            diaryAndHashtagService.deleteAllTagOfDiary(diaryEntity);

            // add new tags
            for (String tag : diaryModifyRequestDto.getHashtag()){
                diaryAndHashtagService.addDiaryAndTag(diaryEntity, tag);
            }
        }

        // delete existing picture
        PictureEntity pictureEntity = pictureRepository.findByDiary(diaryEntity);
        DrawStyleEntity drawStyleEntity = pictureEntity.getDrawStyle();
        pictureRepository.delete(pictureEntity);

        // check draw style
        if (diaryModifyRequestDto.getDraw_style() != null){
            Optional<DrawStyleEntity> drawStyleEntityOptional = drawStyleRepository.findByStyleEng(diaryModifyRequestDto.getDraw_style());
            if (drawStyleEntityOptional.isEmpty()){
                responseDto.setState(HttpStatus.BAD_REQUEST, false, "draw style not match");
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            }
            drawStyleEntity = drawStyleEntityOptional.get();
        }

        // create new picture
        pictureService.createPicture(diaryEntity, drawStyleEntity);


        // response
        responseDto.setState(HttpStatus.OK, true, "success");
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
