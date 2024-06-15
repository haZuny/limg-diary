package com.hayden.limg_diary.api.api_dto;

import com.hayden.limg_diary.api.api_dto.KarloImageForm;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class KarloResponseForm {
    String id;
    String model_version;
    List<KarloImageForm> images;
}
