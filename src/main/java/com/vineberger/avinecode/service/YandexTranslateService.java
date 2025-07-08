package com.vineberger.avinecode.service;

import com.vineberger.avinecode.config.YandexConfig;
import com.vineberger.avinecode.dto.messagesforservices.MessageForExternalServiceDTO;
import com.vineberger.avinecode.dto.messagesforservices.MessageForMyService;
import com.vineberger.avinecode.dto.yandexairesponsemessage.YandexAiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class YandexTranslateService {

    private final RestTemplate REST_TEMPLATE;

    private final YandexConfig CONFIG;

    public MessageForExternalServiceDTO messageConfigurator(MessageForMyService messageForMyService) {
        MessageForExternalServiceDTO messageForExternalServiceDTO = new MessageForExternalServiceDTO();

        messageForExternalServiceDTO.setFolderId(CONFIG.getFolderId());
        messageForExternalServiceDTO.setTexts(List.of(messageForMyService.getText()));
        messageForExternalServiceDTO.setTargetLanguageCode(messageForMyService.getTargetLanguageCode());

        return messageForExternalServiceDTO;
    }

    //interceptor должен быть отдельно. тело пока через интерсептор не добавлять
    public String translateMessage(MessageForExternalServiceDTO messageForExternalServiceDTO) {
        HttpEntity<MessageForExternalServiceDTO> message = new HttpEntity<>(messageForExternalServiceDTO);

        ResponseEntity<YandexAiResponse> yandexAiResponseDTO = REST_TEMPLATE.exchange(
                CONFIG.getYANDEX_BASIC_URL() + "/translate",
                HttpMethod.POST,
                message,
                YandexAiResponse.class
        );

        return yandexAiResponseDTO.getBody().getTranslations().getFirst().getText();
    }
}
