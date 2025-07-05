package com.vineberger.avinecode.service;

import com.vineberger.avinecode.config.YandexConfig;
import com.vineberger.avinecode.dto.yandexairesponsemessage.AvailableLanguage;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TranslationLanguagesListService {
    private final RestTemplate restTemplate;
    private final YandexConfig yandexConfig; // Ваши настройки API

    @Cacheable(value = "languages", sync = true)
    public List<AvailableLanguage> getAllLanguages() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Api-Key " + yandexConfig.getApiKey());

        Map<String, String> request = Map.of("folderId", yandexConfig.getFolderId());

        ResponseEntity<Map> response = restTemplate.postForEntity(
                yandexConfig.YANDEX_AVAILABLE_LANGUAGES_URL,
                new HttpEntity<>(request, headers),
                Map.class
        );

        return ((List<Map<String, String>>) response.getBody().get("languages"))
                .stream()
                .map(item -> new AvailableLanguage(item.get("code"), item.get("name")))
                .toList();
    }

    @CacheEvict(value = "languages", allEntries = true)
    @Scheduled(fixedRate = 24 * 60 * 60 * 1000) // Раз в сутки
    public void refreshCache() {
        // Кэш автоматически очистится
    }
}
