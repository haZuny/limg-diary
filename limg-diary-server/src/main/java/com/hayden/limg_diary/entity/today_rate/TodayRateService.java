package com.hayden.limg_diary.entity.today_rate;

import com.hayden.limg_diary.entity.today_rate.dto.GetTodayrateListResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodayRateService {

    TodayRateRepository todayRateRepository;

    @Autowired
    public TodayRateService(TodayRateRepository todayRateRepository) {
        this.todayRateRepository = todayRateRepository;
    }

    public ResponseEntity<GetTodayrateListResponseDto> getTodayrateList(){

        // find list by repository
        List<TodayRateEntity> todayRateEntityList = todayRateRepository.findAll();

        // set response dto
        GetTodayrateListResponseDto getTodayrateListResponseDto = new GetTodayrateListResponseDto();
        for (TodayRateEntity todayRate : todayRateEntityList){
            getTodayrateListResponseDto.addTodayList(todayRate.getRateNum(), todayRate.getRateStr());
        }

        // return data
        getTodayrateListResponseDto.setState(HttpStatus.OK, true, "success");
        return new ResponseEntity<>(getTodayrateListResponseDto, HttpStatus.OK);
    }
}
