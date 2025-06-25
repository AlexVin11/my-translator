package com.vineberger.avinecode.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "yandex")
@Getter
@Setter
public class ConfigForFolderAndKey {
    private String apiKey;
    private String folderId;
}
