package com.vineberger.avinecode.controller.languageslist;

import com.vineberger.avinecode.config.YandexConfig;
import com.vineberger.avinecode.dto.yandexairesponsemessage.Language;
import com.vineberger.avinecode.service.TranslationLanguagesListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.ServiceUnavailableException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/languages")
@RequiredArgsConstructor
public class AvailableLanguageController {
    private final YandexConfig yandexConfig; //тут урл внешнего сервиса
    private final TranslationLanguagesListService LANGUAGE_LIST_SERVICE;

    @PostMapping
    public ResponseEntity<?> getLanguages() {
        try {
            List<Language> languages = LANGUAGE_LIST_SERVICE.getAvailableLanguages();
            return ResponseEntity.ok(Map.of("languages", languages));
        } catch (ServiceUnavailableException e) {
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(e.getMessage());
        }
    }
}
