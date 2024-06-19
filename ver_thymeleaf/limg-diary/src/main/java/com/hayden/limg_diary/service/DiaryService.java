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
import java.util.Date;
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

    SimpleDateFormat imgNameDateFormat = new SimpleDateFormat("yyyyMMDDHHmmss");
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
    public Optional<Diary> createDiaryWithUserid(DiaryDto.CreateDiaryDto createDiaryDto, int user_id){
        // 유저 탐색
        Optional<User> user = userRepository.findById(user_id);
        if (user.isEmpty()) return Optional.empty();

        // 번역
        Optional<String> transratedText = deeplApiHelper.transrate(createDiaryDto.getContent());

        // 이미지 얻음
        String imgNameDate = imgNameDateFormat.format(date);
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

    public Optional<Diary> createDiaryWithUsername(DiaryDto.CreateDiaryDto createDiaryDto, String username){
        Optional<Integer> userid = userService.getIdFromUsername(username);
        if (userid.isPresent()){
            return createDiaryWithUserid(createDiaryDto, userid.get());
        }
        return Optional.empty();
    }

    public Optional<Diary> getByDiaryid(int diaryid){
        return diaryRepository.findById(diaryid);
    }

    public Optional<Diary> findByUserAanDate(User user, LocalDate date){
        return Optional.ofNullable(diaryRepository.findByUserAndDate(user, date));
    }
}
