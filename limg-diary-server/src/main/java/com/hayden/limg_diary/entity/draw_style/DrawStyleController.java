package com.hayden.limg_diary.entity.draw_style;

import com.hayden.limg_diary.entity.draw_style.dto.GetDrawstyleResopnseDto;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/drawstyle")
public class DrawStyleController {

    DrawStyleService drawStyleService;

    @Autowired
    public DrawStyleController(DrawStyleService drawStyleService) {
        this.drawStyleService = drawStyleService;
    }

    @GetMapping("/list")
    public ResponseEntity<GetDrawstyleResopnseDto> getDrawstyleList(){
        return drawStyleService.getList();
    }

}
