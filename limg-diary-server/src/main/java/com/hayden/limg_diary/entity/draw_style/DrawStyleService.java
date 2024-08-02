package com.hayden.limg_diary.entity.draw_style;

import com.hayden.limg_diary.entity.draw_style.dto.GetDrawstyleResopnseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DrawStyleService {

    DrawStyleRepository drawStyleRepository;

    @Autowired
    public DrawStyleService(DrawStyleRepository drawStyleRepository) {
        this.drawStyleRepository = drawStyleRepository;
    }

    public ResponseEntity<GetDrawstyleResopnseDto> getList(){

        GetDrawstyleResopnseDto resopnseDto = new GetDrawstyleResopnseDto();

        List<DrawStyleEntity> drawStyleList = drawStyleRepository.findAll();
        for (DrawStyleEntity drawStyle : drawStyleList){
            resopnseDto.addData(drawStyle.getStyleEng(), drawStyle.getStyleKor());
        }

        resopnseDto.setState(HttpStatus.OK, true, "success");
        return new ResponseEntity<GetDrawstyleResopnseDto>(resopnseDto, HttpStatus.OK);
    }
}
