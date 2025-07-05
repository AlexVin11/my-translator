package com.vineberger.avinecode.service;

import com.vineberger.avinecode.model.User;
import com.vineberger.avinecode.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsManager {
    private final UserRepository USER_REPOSITORY;
    private final PasswordEncoder PASSWORD_ENCODER;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = USER_REPOSITORY.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User with username: " + username + " not found."));
        return user;
    }

    @Override
    public void createUser(UserDetails user) {

    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        User user = USER_REPOSITORY.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User with username: " + username + " not found."));
        return Objects.equals(user, null) ? false : true;
    }
}
