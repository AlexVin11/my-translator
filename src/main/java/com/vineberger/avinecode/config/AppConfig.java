package com.vineberger.avinecode.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Getter
@Setter
public class AppConfig {
    private String publicKeyPem;
    private String privateKeyPem;
    private String yandexApiKey;
    private String yandexFolderId;

    @PostConstruct
    public void init() {
        // Получаем переменные только из окружения
        this.publicKeyPem = System.getenv("RSA_PUBLIC_PEM");
        this.privateKeyPem = System.getenv("RSA_PRIVATE_PEM");
        this.yandexApiKey = System.getenv("YANDEX_API_KEY");
        this.yandexFolderId = System.getenv("YANDEX_FOLDER_ID");

        validateEnvVars();
        log.info("Application config loaded from environment variables");
    }

    private void validateEnvVars() {
        StringBuilder missingVars = new StringBuilder();

        if (publicKeyPem == null) missingVars.append("RSA_PUBLIC_PEM ");
        if (privateKeyPem == null) missingVars.append("RSA_PRIVATE_PEM ");
        if (yandexApiKey == null) missingVars.append("YANDEX_API_KEY ");
        if (yandexFolderId == null) missingVars.append("YANDEX_FOLDER_ID ");

        if (missingVars.length() > 0) {
            String errorMsg = "Missing required environment variables: " + missingVars;
            log.error(errorMsg);
            throw new IllegalStateException(errorMsg);
        }
    }
}
