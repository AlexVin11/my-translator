package com.vineberger.avinecode.service;

import com.vineberger.avinecode.config.YandexConfig;
import com.vineberger.avinecode.dto.yandexairesponsemessage.Language;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.naming.ServiceUnavailableException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TranslationLanguagesListService {
    private final RestTemplate REST_TEMPLATE;
    private final YandexConfig YANDEX_CONFIG;

    @Cacheable(value = "languages", unless = "#result.isEmpty()")
    public List<Language> getAvailableLanguages() throws ServiceUnavailableException {
        try {
            // Явно создаем тело запроса с folderId
            Map<String, String> requestBody = new HashMap<>();
            if (YANDEX_CONFIG.getFolderId() != null) {
                requestBody.put("folderId", YANDEX_CONFIG.getFolderId());
            }

            HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody);

            ResponseEntity<Map> response = REST_TEMPLATE.exchange(
                    YANDEX_CONFIG.getYANDEX_BASIC_URL() + "/languages",
                    HttpMethod.POST,
                    entity,
                    Map.class
            );

            List<Map<String, String>> languages = (List<Map<String, String>>) response.getBody().get("languages");
            return languages.stream()
                    .map(lang -> new Language(lang.get("code"), lang.get("name")))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Failed to get languages from Yandex", e);
            throw new ServiceUnavailableException("Сервис языков временно недоступен");
        }
    }

    @CacheEvict(value = "languages", allEntries = true)
    @Scheduled(fixedRateString = "${yandex.cache.refresh.interval:86400000}")
    public void refreshCache() {
        log.info("Clearing languages cache");
    }
}
