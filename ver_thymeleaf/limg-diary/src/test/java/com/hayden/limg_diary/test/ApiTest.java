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
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ApiTest {
    @Autowired
    DeeplApiHelper deeplApiHelper;

    @Autowired
    KarloApiHelper karloApiHelper;

    @Test
    public void karloApiTest(){

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
    public void test() throws IOException {
        String OUTPUT_FILE_PATH = "C:\\Users\\gkwns\\OneDrive\\바탕 화면\\test\\test.webp";
        String FILE_URL = "https://mk.kakaocdn.net/dna/karlo/image/2024-06-15/15/22204f1e-f0af-4851-a9a4-ff7462a78060.webp?credential=smxRqiqUEJBVgohptvfXS5JoYeFv4Xxa&expires=1718434291&signature=J6KW4WR9C14dWpVxNcD2xYeexoI%3D";

        karloApiHelper.saveImageUrl(FILE_URL, OUTPUT_FILE_PATH);
    }


}
