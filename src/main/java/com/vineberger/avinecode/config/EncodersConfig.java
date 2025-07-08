package com.vineberger.avinecode.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.vineberger.avinecode.component.RsaKeyProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.util.Assert;

import java.util.UUID;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class EncodersConfig {
    private final RsaKeyProperties rsaKeyProperties;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        try {
            Assert.notNull(rsaKeyProperties.getRsaPublicKey(), "RSA public key cannot be null");
            Assert.notNull(rsaKeyProperties.getRsaPrivateKey(), "RSA private key cannot be null");

            log.debug("Creating JwtEncoder with RSA keys");

            RSAKey rsaKey = new RSAKey.Builder(rsaKeyProperties.getRsaPublicKey())
                    .privateKey(rsaKeyProperties.getRsaPrivateKey())
                    .keyID(UUID.randomUUID().toString())
                    .build();

            JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(rsaKey));
            return new NimbusJwtEncoder(jwkSource);

        } catch (Exception e) {
            log.error("Failed to create JwtEncoder", e);
            throw new IllegalStateException("Failed to initialize JWT encoder", e);
        }
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        try {
            Assert.notNull(rsaKeyProperties.getRsaPublicKey(), "RSA public key cannot be null");

            log.debug("Creating JwtDecoder with RSA public key");

            return NimbusJwtDecoder.withPublicKey(rsaKeyProperties.getRsaPublicKey())
                    .signatureAlgorithm(SignatureAlgorithm.RS256)
                    .build();

        } catch (Exception e) {
            log.error("Failed to create JwtDecoder", e);
            throw new IllegalStateException("Failed to initialize JWT decoder", e);
        }
    }
}
