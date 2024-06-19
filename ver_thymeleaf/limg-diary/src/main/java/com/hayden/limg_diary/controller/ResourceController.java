package com.hayden.limg_diary.controller;

import com.hayden.limg_diary.entity.Diary;
import com.hayden.limg_diary.entity.User;
import com.hayden.limg_diary.service.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;
import java.util.Optional;

@Controller
public class ResourceController {

    @Value("${path.res.img.default-img}")
    String defauldDiaryImg;

    DiaryService diaryService;

    @Autowired
    public ResourceController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @ResponseBody
    @GetMapping("/resource/diaryimg/{diaryid}")
    public Resource getDiaryImage(@PathVariable int diaryid, @AuthenticationPrincipal User user) throws MalformedURLException, IllegalAccessException {
        Optional<Diary> diary = diaryService.getByDiaryid(diaryid);
        if (diary.get().getUserid().getUserid() != (user.getUserid())){
            throw new IllegalAccessException("올바르지 못한 접근");
        }
        String imgPath = diaryService.getByDiaryid(diaryid).get().getImage_path();
        return new UrlResource("file:" + imgPath);
    }

    @ResponseBody
    @GetMapping("/resource/diaryimg/default")
    public Resource getDiaryImage() throws MalformedURLException {
        return new UrlResource("file:" + defauldDiaryImg);
    }
}
