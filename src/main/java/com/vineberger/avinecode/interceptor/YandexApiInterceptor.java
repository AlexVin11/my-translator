package com.vineberger.avinecode.interceptor;

import com.vineberger.avinecode.config.YandexConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class YandexApiInterceptor implements ClientHttpRequestInterceptor {

    private final YandexConfig YANDEX_CONFIG;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        request.getHeaders().set("Authorization", "Api-Key " + YANDEX_CONFIG.getApiKey());
        return execution.execute(request, body);
    }
}
