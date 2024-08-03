package com.hayden.limg_diary;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@RestController
public class TestController {
    @GetMapping(value = "/test", produces = MediaType.IMAGE_PNG_VALUE)
    public Resource getTest() throws MalformedURLException {

        return new UrlResource("file:" + "C:\\Users\\gkwns\\Pictures\\Screenshots\\스크린샷 2024-07-25 005854.png");
    }

    @GetMapping("/test/admin")
    @ResponseBody
    public String getTestAdmin(){
        return "success";
    }

}
