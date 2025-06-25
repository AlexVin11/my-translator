package com.vineberger.avinecode.dto.yandexaiservice;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Translation {
    private String text;
    private String detectedLanguageCode;
}
