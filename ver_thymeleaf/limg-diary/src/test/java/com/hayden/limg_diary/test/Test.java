package com.hayden.limg_diary.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hayden.limg_diary.api.KarloApiHelper;
import com.hayden.limg_diary.api.KarloImageForm;
import com.hayden.limg_diary.api.KarloResponseForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@SpringBootTest
public class Test {
    @Autowired
    KarloApiHelper apiHelper;

    @org.junit.jupiter.api.Test
    void test() throws JsonProcessingException {
        KarloResponseForm res = apiHelper.post("I run");

        System.out.println(res.getImages().get(0).getImage());
//        ObjectMapper objectMapper = new ObjectMapper();
//        KarloImageForm[] ls = objectMapper.readValue(res.getImages(), KarloImageForm[].class);
//        System.out.println(ls[0].getImages());
    }
}
