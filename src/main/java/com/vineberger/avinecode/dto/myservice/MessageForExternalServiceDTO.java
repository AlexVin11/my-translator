package com.vineberger.avinecode.dto.myservice;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MessageForExternalServiceDTO {
    private String folderId;
    private List<String> texts;
    private String targetLanguageCode;
}
