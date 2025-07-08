package com.vineberger.avinecode.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "yandex")
@Getter
@Setter
@Slf4j
public class YandexConfig {

    private String apiKey;

    private String folderId;

    private final String YANDEX_BASIC_URL = "https://translate.api.cloud.yandex.net/translate/v2";

    /*private String yandexBasicUrl = ;

    public final String YANDEX_TRANSLATE_URL = yandexBasicUrl + "/translate";

    public final String YANDEX_AVAILABLE_LANGUAGES_URL = yandexBasicUrl + "/languages";*/

    @PostConstruct
    public void init() {
        if (getApiKey() == null
                || getFolderId() == null
                /*|| getYandexBasicUrl() == null
                || getYANDEX_TRANSLATE_URL().equals("/translate")
                || getYANDEX_AVAILABLE_LANGUAGES_URL().equals("/languages")*/) {

            throw new IllegalStateException("Yandex API configuration is not set properly!");

        }
    }
}
