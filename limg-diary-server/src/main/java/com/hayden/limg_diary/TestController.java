package com.hayden.limg_diary;

import com.hayden.limg_diary.entity.user.dto.DefaultResponseDto;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@RestController
public class TestController {
    @GetMapping(value = "/test")
    public ResponseEntity getTest() throws MalformedURLException {
        DefaultResponseDto defaultResponseDto = new DefaultResponseDto();
        defaultResponseDto.setMember(HttpStatus.OK, true, "success");
        return new ResponseEntity<>(defaultResponseDto, HttpStatus.OK);
    }

    @GetMapping("/test/admin")
    @ResponseBody
    public String getTestAdmin(){
        return "success";
    }

}
