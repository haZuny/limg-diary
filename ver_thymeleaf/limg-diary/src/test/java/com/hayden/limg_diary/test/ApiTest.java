package com.hayden.limg_diary.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hayden.limg_diary.api.DeeplApiHelper;
import com.hayden.limg_diary.api.KarloApiHelper;
import com.hayden.limg_diary.api.api_dto.DeeplResponseForm;
import com.hayden.limg_diary.api.api_dto.KarloImageForm;
import com.hayden.limg_diary.api.api_dto.KarloResponseForm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ApiTest {
    @Autowired
    DeeplApiHelper deeplApiHelper;

    @Autowired
    KarloApiHelper karloApiHelper;

    @Test
    public void karloApiTest() {

        String prompt = "hello";

        // POST 메소드 체크
        Optional<KarloResponseForm> responseForm = karloApiHelper.post(prompt);
        Assertions.assertNotNull(responseForm.orElse(null));

        // getImages() 메소드 체크
        List<KarloImageForm> imageForms = responseForm.get().getImages();
        Assertions.assertNotNull(imageForms);
        Assertions.assertNotEquals(0, imageForms.size());

        // getFirst() 메소드 체크
        KarloImageForm firstImageForm = imageForms.getFirst();
        String imgUrl = firstImageForm.getImage();
        Assertions.assertNotNull(imgUrl);
        Assertions.assertNotEquals("", imgUrl);

        System.out.println(imgUrl);

    }

    @Test
    public void 이미지저장테스트() {
        // When
        Optional<String> res = karloApiHelper.createAndSaveImage("hello", "sample.webp");

        // Then
        Assertions.assertNotNull(res.orElse(""));
    }


}
