package com.hayden.limg_diary.entity.challenges.dto;

import com.hayden.limg_diary.entity.DefaultResponseDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class GetByIdResponseDto extends DefaultResponseDto {
    ChallengeObj data = new ChallengeObj();

    @Data
    public class ChallengeObj{
        private  int id;
        private String name;
        private String specific;
        private String achievedIconPath;
        private String unachievedIconPath;

        public void setDataValue(int id, String name, String specific, String achievedIconPath, String unachievedIconPath){
            this.id = id;
            this. name = name;
            this.specific = specific;
            this.achievedIconPath = achievedIconPath;
            this.unachievedIconPath = unachievedIconPath;
        }
    }

}
