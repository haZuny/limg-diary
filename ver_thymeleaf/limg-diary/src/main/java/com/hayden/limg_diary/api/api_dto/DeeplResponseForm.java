package com.hayden.limg_diary.api.api_dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeeplResponseForm {
    List<DeeplTransitionForm> translations;
}
