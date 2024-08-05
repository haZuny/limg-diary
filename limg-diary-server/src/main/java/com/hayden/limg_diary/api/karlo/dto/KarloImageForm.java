package com.hayden.limg_diary.api.karlo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KarloImageForm {
    String id;
    String image;
    String seed;
    String nsfw_content_detected;
    String nsfw_score;
}
