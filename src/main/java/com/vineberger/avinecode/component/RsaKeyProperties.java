package com.vineberger.avinecode.component;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

@Component
@Slf4j
@ConfigurationProperties(prefix = "rsa")
@Setter
@Getter
@RequiredArgsConstructor
public final class RsaKeyProperties {
    private final Environment environment;
    // Загруженные ключи
    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;

    @PostConstruct
    public void init() {
        try {
            // 1. Попробовать загрузить из переменных окружения
            String publicPem = System.getenv("RSA_PUBLIC_KEY");
            String privatePem = System.getenv("RSA_PRIVATE_KEY");

            if (publicPem != null && privatePem != null) {
                this.publicKey = parsePublicKey(publicPem);
                this.privateKey = parsePrivateKey(privatePem);
                log.info("RSA keys loaded from environment variables");
                return;
            }

            // 2. Для dev-режима - загрузка из файлов
            if (Arrays.asList(environment.getActiveProfiles()).contains("dev")) {
                log.warn("DEV MODE: Loading RSA keys from classpath");
                this.publicKey = parsePublicKey(readClasspathFile("certs/public.pem"));
                this.privateKey = parsePrivateKey(readClasspathFile("certs/private.pem"));
                return;
            }

            throw new IllegalStateException("RSA keys not configured");

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize RSA keys", e);
        }
    }

    private String readClasspathFile(String path) throws IOException {
        try (InputStream is = new ClassPathResource(path).getInputStream()) {
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    private RSAPublicKey parsePublicKey(String pem) throws Exception {
        String publicKeyPEM = pem.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    private RSAPrivateKey parsePrivateKey(String pem) throws Exception {
        StringReader reader = new StringReader(pem);
        PEMParser pemParser = new PEMParser(reader);
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        PrivateKeyInfo privateKeyInfo = PrivateKeyInfo.getInstance(pemParser.readObject());
        return (RSAPrivateKey) converter.getPrivateKey(privateKeyInfo);
    }
}
