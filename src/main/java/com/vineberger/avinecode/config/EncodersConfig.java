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
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.util.Assert;

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
            Assert.notNull(rsaKeyProperties.getPublicKey(), "RSA public key cannot be null");
            Assert.notNull(rsaKeyProperties.getPrivateKey(), "RSA private key cannot be null");

            log.debug("Creating JwtEncoder with RSA keys");

            JWK jwk = new RSAKey.Builder(rsaKeyProperties.getPublicKey())
                    .privateKey(rsaKeyProperties.getPrivateKey())
                    .build();

            JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
            return new NimbusJwtEncoder(jwks);

        } catch (Exception e) {
            log.error("Failed to create JwtEncoder", e);
            throw new IllegalStateException("Failed to initialize JWT encoder", e);
        }
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        try {
            Assert.notNull(rsaKeyProperties.getPublicKey(), "RSA public key cannot be null");

            log.debug("Creating JwtDecoder with RSA public key");

            return NimbusJwtDecoder.withPublicKey(rsaKeyProperties.getPublicKey())
                    .build();

        } catch (Exception e) {
            log.error("Failed to create JwtDecoder", e);
            throw new IllegalStateException("Failed to initialize JWT decoder", e);
        }
    }
}
