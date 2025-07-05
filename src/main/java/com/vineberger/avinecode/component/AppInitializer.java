package com.vineberger.avinecode.component;

import com.vineberger.avinecode.config.EncodersConfig;
import com.vineberger.avinecode.model.User;
import com.vineberger.avinecode.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppInitializer {
    private final String ADMIN_EMAIL = "mycustomadmin@qq.com";
    private final String ADMIN_PASSWORD = "testSillyA$$";
    private final UserRepository USER_REPOSITORY;
    private final EncodersConfig ENCODERS_CONFIG;

    @Bean
    CommandLineRunner setAdmin() {
        return args -> {
            var foundUser = USER_REPOSITORY.findByEmail(ADMIN_EMAIL);
            if (!foundUser.isPresent()) {
                System.out.println("Creating admin user...");
                User admin = new User();
                String hashedPassword = ENCODERS_CONFIG.passwordEncoder().encode(ADMIN_PASSWORD);
                admin.setEmail(ADMIN_EMAIL);
                admin.setPassword(hashedPassword);
                USER_REPOSITORY.save(admin);
                System.out.println("Admin created successfully.");
                System.out.println("Hashed password: " + hashedPassword);
            }
        };
    }
}
