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

    public final String YANDEX_TRANSLATE_URL = YANDEX_BASIC_URL + "/translate";

    public final String YANDEX_AVAILABLE_LANGUAGES_URL = YANDEX_BASIC_URL + "/languages";

    @PostConstruct
    public void init() {
        log.info("API Key: {}", getApiKey());
        log.info("Folder ID: {}", getFolderId());

        // Дополнительная проверка
        if (getApiKey() == null || getFolderId() == null) {
            throw new IllegalStateException("Yandex API configuration is not set properly!");
        }
    }
}
