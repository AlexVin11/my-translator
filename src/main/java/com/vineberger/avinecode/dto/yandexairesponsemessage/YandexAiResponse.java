package com.vineberger.avinecode.dto.yandexairesponsemessage;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class YandexAiResponse {
    private List<Translation> translations;
}
