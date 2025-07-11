package com.vineberger.avinecode.utils;

import com.vineberger.avinecode.model.User;
import com.vineberger.avinecode.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public final class UserUtils {
    private final UserRepository userRepository;

    public User getCurrentUser() {
        var authentification = SecurityContextHolder.getContext().getAuthentication();
        if (authentification == null || !authentification.isAuthenticated()) {
            return null;
        }
        var email = authentification.getName();
        return userRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("User with email: " + email + " not found"));
    }

    public boolean isCurrentUser(Long id) {
        var emailOfUser = userRepository.findById(id).orElseThrow(
                        () -> new ResourceNotFoundException("User not found by id: " + id))
                .getEmail();
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "User  is not authenticated.");
        }
        if (!emailOfUser.equals(authentication.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Operation not allowed: user tries to operate with another user."
                            + "User can operate only with himself");
        }
        return true;
    }

    public boolean userHasAuthentication() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }

    public User getTestUser() {
        return  userRepository.findByEmail("mycusadmn@qq.com")
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
