package com.vineberger.avinecode.config;

import com.vineberger.avinecode.interceptor.YandexApiInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {

    private final YandexApiInterceptor INTERCEPTOR;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(List.of(INTERCEPTOR));
        return restTemplate;
    }
}
