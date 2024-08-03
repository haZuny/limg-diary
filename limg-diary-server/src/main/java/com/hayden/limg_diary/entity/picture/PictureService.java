package com.hayden.limg_diary.entity.picture;

import com.hayden.limg_diary.api.deepl.DeeplApiHelper;
import com.hayden.limg_diary.api.karlo.KarloApiHelper;
import com.hayden.limg_diary.entity.diary.DiaryEntity;
import com.hayden.limg_diary.entity.draw_style.DrawStyleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PictureService {
    PictureRepository pictureRepository;
    KarloApiHelper karloApiHelper;
    DeeplApiHelper deeplApiHelper;

    @Value("${path.resources}")
    String resourceDirPath;

    @Autowired
    public PictureService(PictureRepository pictureRepository, KarloApiHelper karloApiHelper, DeeplApiHelper deeplApiHelper) {
        this.pictureRepository = pictureRepository;
        this.karloApiHelper = karloApiHelper;
        this.deeplApiHelper = deeplApiHelper;
    }

    // create picture
    public boolean createPicture(DiaryEntity diary, DrawStyleEntity drawStyle){
        // transrate
        Optional<String> transratedOptional = deeplApiHelper.transrate(diary.getContent());
        if (transratedOptional.isEmpty())   return false;

        // create karlo prompt
        String imgPrompt = drawStyle.getStyleEng() + transratedOptional.get();

        // create img
        String savePath = String.format("%s\\img\\diary_img\\%d\\%d.png", resourceDirPath, diary.getUser().getId(), diary.getId());
        boolean karloRes = karloApiHelper.createAndSaveImage(imgPrompt, savePath);
        if (!karloRes)  return false;

        // save picture entity
        PictureEntity pictureEntity = new PictureEntity();
        pictureEntity.setPath(String.format("\\img\\diary_img\\%d\\%d.png", diary.getUser().getId(), diary.getId()));
        pictureEntity.setDiary(diary);
        pictureEntity.setDrawStyle(drawStyle);
        pictureRepository.save(pictureEntity);
        return true;
    }
}
