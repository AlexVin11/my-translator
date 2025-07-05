package com.vineberger.avinecode.controller.languageslist;

import com.vineberger.avinecode.dto.yandexairesponsemessage.AvailableLanguage;
import com.vineberger.avinecode.service.TranslationLanguagesListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/languages")
@RequiredArgsConstructor
public class AvailableLanguageController {
    private final TranslationLanguagesListService languagesListService;

    @GetMapping
    public ResponseEntity<List<AvailableLanguage>> getLanguages() {
        return ResponseEntity.ok(languagesListService.getAllLanguages());
    }
}
