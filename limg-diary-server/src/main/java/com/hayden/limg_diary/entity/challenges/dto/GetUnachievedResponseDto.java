package com.hayden.limg_diary.entity.challenges.dto;

import com.hayden.limg_diary.entity.DefaultResponseDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.UrlResource;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GetUnachievedResponseDto extends DefaultResponseDto {

    List<GetUnachievedResponseDto.DataClass> data = new ArrayList<>();

    public void addData(int challenge_id, String icon_path, String name, String specific) throws MalformedURLException {
        this.data.add(new GetUnachievedResponseDto.DataClass(challenge_id, icon_path, name, specific));
    }

    @Data
    private class DataClass{
        int challenge_id;
        UrlResource icon_path;
        String name;
        String specific;

        private DataClass(int challenge_id, String icon_path, String name, String specific) throws MalformedURLException {
            this.challenge_id = challenge_id;
            this.icon_path = new UrlResource("file:"+icon_path);
            this.name = name;
            this.specific = specific;
        }
    }
}
