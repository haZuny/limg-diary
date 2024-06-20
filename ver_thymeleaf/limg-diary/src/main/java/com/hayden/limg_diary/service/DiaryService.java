package com.hayden.limg_diary.service;

import com.hayden.limg_diary.api.DeeplApiHelper;
import com.hayden.limg_diary.api.KarloApiHelper;
import com.hayden.limg_diary.dto.DiaryDto;
import com.hayden.limg_diary.entity.Diary;
import com.hayden.limg_diary.entity.User;
import com.hayden.limg_diary.repository.DiaryRepository;
import com.hayden.limg_diary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DiaryService {


    @Value("${path.res.img.default-img}")
    String defaultImgPath;

    UserService userService;
    UserRepository userRepository;
    DiaryRepository diaryRepository;
    DeeplApiHelper deeplApiHelper;
    KarloApiHelper karloApiHelper;

    Date date = new Date();

    @Autowired
    public DiaryService(UserService userService, UserRepository userRepository, DiaryRepository diaryRepository,
                        DeeplApiHelper deeplApiHelper, KarloApiHelper karloApiHelper) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.diaryRepository = diaryRepository;
        this.deeplApiHelper = deeplApiHelper;
        this.karloApiHelper = karloApiHelper;
    }

    // 일기 작성
    public Optional<Diary> createDiaryFromUserid(DiaryDto.CreateDiaryDto createDiaryDto, int user_id){
        // 유저 탐색
        Optional<User> user = userRepository.findById(user_id);
        if (user.isEmpty()) return Optional.empty();

        // 번역
        Optional<String> transratedText = deeplApiHelper.transrate(createDiaryDto.getContent());

        // 이미지 얻음
        String imgNameDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd-HH-mm-ss"));
        String imgName = Integer.toString(user_id) + "_" + imgNameDate + ".webp";
        Optional<String> imgPath = karloApiHelper.createAndSaveImage(transratedText.orElse(""), imgName);

        // Diary Entity 생성
        Diary diary = new Diary();
        diary.setContent(createDiaryDto.getContent());
        diary.setFeeling(createDiaryDto.getFeeling());
        diary.setUserid(user.get());
        if(imgPath.isPresent()){
            diary.setImage_path(imgPath.get());
        } else{
            diary.setImage_path(defaultImgPath);
        }


        // DB 저장
        try{
            diaryRepository.save(diary);
            return Optional.of(diary);
        } catch (Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Diary> createDiaryFromUseridWithDefaultImage(DiaryDto.CreateDiaryDto createDiaryDto, int user_id, LocalDateTime date){
        // 유저 탐색
        Optional<User> user = userRepository.findById(user_id);
        if (user.isEmpty()) return Optional.empty();

        // 번역
        Optional<String> transratedText = deeplApiHelper.transrate(createDiaryDto.getContent());

        // Diary Entity 생성
        Diary diary = new Diary();
        diary.setContent(createDiaryDto.getContent());
        diary.setFeeling(createDiaryDto.getFeeling());
        diary.setUserid(user.get());
        diary.setImage_path(defaultImgPath);
        diary.setDate(Date.from(date.atZone(ZoneId.systemDefault()).toInstant()));

        // DB 저장
        try{
            diaryRepository.save(diary);
            return Optional.of(diary);
        } catch (Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Diary> createDiaryFromUserid(DiaryDto.CreateDiaryDto createDiaryDto, int user_id, LocalDateTime date){
        // 유저 탐색
        Optional<User> user = userRepository.findById(user_id);
        if (user.isEmpty()) return Optional.empty();

        // 번역
        Optional<String> transratedText = deeplApiHelper.transrate(createDiaryDto.getContent());

        // 이미지 얻음
        String imgNameDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd-HH-mm-ss"));
        String imgName = Integer.toString(user_id) + "_" + imgNameDate + ".webp";
        Optional<String> imgPath = karloApiHelper.createAndSaveImage(transratedText.orElse(""), imgName);

        // Diary Entity 생성
        Diary diary = new Diary();
        diary.setContent(createDiaryDto.getContent());
        diary.setFeeling(createDiaryDto.getFeeling());
        diary.setUserid(user.get());
        diary.setDate(Date.from(date.atZone(ZoneId.systemDefault()).toInstant()));
        if(imgPath.isPresent()){
            diary.setImage_path(imgPath.get());
        } else{
            diary.setImage_path(defaultImgPath);
        }


        // DB 저장
        try{
            diaryRepository.save(diary);
            return Optional.of(diary);
        } catch (Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Diary> createDiaryFromUseridWithDefaultImage(DiaryDto.CreateDiaryDto createDiaryDto, int user_id){
        // 유저 탐색
        Optional<User> user = userRepository.findById(user_id);
        if (user.isEmpty()) return Optional.empty();

        // 번역
        Optional<String> transratedText = deeplApiHelper.transrate(createDiaryDto.getContent());

        // Diary Entity 생성
        Diary diary = new Diary();
        diary.setContent(createDiaryDto.getContent());
        diary.setFeeling(createDiaryDto.getFeeling());
        diary.setUserid(user.get());
        diary.setImage_path(defaultImgPath);

        // DB 저장
        try{
            diaryRepository.save(diary);
            return Optional.of(diary);
        } catch (Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Diary> updateDiaryExceptImage(DiaryDto.UpdateDiaryDto updateDiaryDto){
        Optional<Diary> diaryOp = diaryRepository.findById(updateDiaryDto.getDiaryid());
        Diary diary = diaryOp.get();
        diary.setContent(updateDiaryDto.getContent());
        diary.setFeeling(updateDiaryDto.getFeeling());
        return Optional.of(diaryRepository.save(diary));
    }

    public Optional<Diary> updateDiaryWithImage(DiaryDto.UpdateDiaryDto updateDiaryDto){
        Optional<Diary> diaryOp = diaryRepository.findById(updateDiaryDto.getDiaryid());
        Diary diary = diaryOp.get();

        // 번역
        Optional<String> transratedText = deeplApiHelper.transrate(updateDiaryDto.getContent());

        // 이미지 얻음
        String imgNameDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd-HH-mm-ss"));
        String imgName = Integer.toString(diary.getUserid().getUserid()) + "_" + imgNameDate + ".webp";
        Optional<String> imgPath = karloApiHelper.createAndSaveImage(transratedText.orElse(""), imgName);

        // 엔티티 업데이트
        diary.setContent(updateDiaryDto.getContent());
        diary.setFeeling(updateDiaryDto.getFeeling());
        if(imgPath.isPresent()){
            diary.setImage_path(imgPath.get());
        } else{
            diary.setImage_path(defaultImgPath);
        }

        return Optional.of(diaryRepository.save(diary));
    }


    public Optional<Diary> getByDiaryid(int diaryid){
        return diaryRepository.findById(diaryid);
    }

    public Optional<Diary> findByUserAndDate(User user, LocalDate date){
        return Optional.ofNullable(diaryRepository.findByUserAndDate(user, date));
    }

    public List<Diary> findByUserAndDateBetween(User user, LocalDate sdate, LocalDate edate){
        return diaryRepository.findAllByUseridAndDateBetween(user
                , LocalDateTime.of(sdate, LocalTime.of(0,0,0))
                , LocalDateTime.of(edate, LocalTime.of(23,59,59)));
    }
}
