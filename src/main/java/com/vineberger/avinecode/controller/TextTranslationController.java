package com.vineberger.avinecode.controller;

import com.vineberger.avinecode.dto.myservice.MessageForExternalServiceDTO;
import com.vineberger.avinecode.dto.myservice.MessageForMyService;
import com.vineberger.avinecode.service.YandexTranslateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/translate")
@RequiredArgsConstructor
public class TextTranslationController {
    private final YandexTranslateService service;

    @PostMapping()
    public String translate(@RequestBody MessageForMyService message) throws Exception {
        MessageForExternalServiceDTO messageForExternalServiceDTO = service.messageConfigurator(message);
        return service.translateMessage(messageForExternalServiceDTO);
    }
}
