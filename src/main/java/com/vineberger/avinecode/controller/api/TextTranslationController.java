package com.vineberger.avinecode.controller.api;

import com.vineberger.avinecode.dto.messagesforservices.MessageForExternalServiceDTO;
import com.vineberger.avinecode.dto.messagesforservices.MessageForMyService;
import com.vineberger.avinecode.service.YandexTranslateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/translate")
@RequiredArgsConstructor
public class TextTranslationController {
    private final YandexTranslateService SERVICE;

    @PostMapping()
    public Map<String, String> translate(@RequestBody MessageForMyService message) throws Exception {
        MessageForExternalServiceDTO dto = SERVICE.messageConfigurator(message);
        String translation = SERVICE.translateMessage(dto);

        return Collections.singletonMap("translatedText", translation);
    }
}
