package com.hayden.limg_diary.test;


import com.hayden.limg_diary.entity.Diary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SimpleTest {
    @Value("${path.res.img.default-img}")
    String path;

    @Test
    public void Test(){
        Diary diary = new Diary();
    }
}
