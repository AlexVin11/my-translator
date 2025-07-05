package com.vineberger.avinecode.interceptor;

import com.vineberger.avinecode.config.YandexConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

@RequiredArgsConstructor
public class YandexApiInterceptor implements ClientHttpRequestInterceptor {

    private final YandexConfig yandexConfig;

    public ClientHttpResponse intercept(HttpRequest request,
                                        byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {

        request.getHeaders().set("Authorization", "Api-Key " + yandexConfig.getApiKey());

        return execution.execute(request, body);
    }
}
