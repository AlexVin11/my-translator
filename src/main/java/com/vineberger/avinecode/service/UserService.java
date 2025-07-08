package com.vineberger.avinecode.service;

import com.vineberger.avinecode.config.EncodersConfig;
import com.vineberger.avinecode.dto.UserDTO.UserCreateDTO;
import com.vineberger.avinecode.dto.UserDTO.UserDTO;
import com.vineberger.avinecode.dto.UserDTO.UserUpdateDTO;
import com.vineberger.avinecode.mapper.UserMapper;
import com.vineberger.avinecode.model.User;
import com.vineberger.avinecode.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public final class UserService {

    private final EncodersConfig encodersConfig;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDTO> getAll() {
        var users = userRepository.findAll();
        var result = users.stream()
                .map(userMapper::map)
                .toList();
        return result;
    }

    public UserDTO getCreatedByDefaultUser() {
        User user = userRepository.findByEmail("mycustomadmin@qq.com")
                .orElseThrow(() -> new ResourceNotFoundException("User with default email not found"));
        var dto = userMapper.map(user);
        return dto;
    }

    public UserDTO getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " not found"));
        var dto = userMapper.map(user);
        return dto;
    }

    public UserDTO create(UserCreateDTO userData) {
        User user = userMapper.map(userData);
        PasswordEncoder encoder = encodersConfig.passwordEncoder();
        String cryptedPassword = encoder.encode(user.getPassword());
        user.setPassword(cryptedPassword);
        userRepository.save(user);
        var dto = userMapper.map(user);
        return dto;
    }

    public UserDTO update(UserUpdateDTO userData, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " not found"));
        if (userData.getPassword() != null && userData.getPassword().isPresent()) {
            PasswordEncoder encoder = encodersConfig.passwordEncoder();
            var cryptedPassword = encoder.encode(userData.getPassword().get());
            user.setPassword(cryptedPassword);
        }
        userMapper.update(userData, user);
        userRepository.save(user);
        var dto = userMapper.map(user);
        return dto;
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
