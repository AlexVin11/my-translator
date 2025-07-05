package com.vineberger.avinecode;

import com.vineberger.avinecode.component.RsaKeyProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
@Slf4j
public class MyCustomTranslatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyCustomTranslatorApplication.class, args);
	}

	@Bean
	CommandLineRunner checkKeys(RsaKeyProperties keys) {
		return args -> {
			if (keys.getPublicKey() == null || keys.getPrivateKey() == null) {
				throw new IllegalStateException("RSA keys not initialized!");
			}
			log.info("✓ RSA keys initialized successfully");
		};
	}
}
