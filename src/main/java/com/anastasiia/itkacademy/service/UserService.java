package com.anastasiia.itkacademy.service;

import java.util.UUID;

import com.anastasiia.itkacademy.dto.UserRequest;
import com.anastasiia.itkacademy.entity.Role;
import com.anastasiia.itkacademy.entity.User;
import com.anastasiia.itkacademy.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Transactional(readOnly = true)
    public String getInfoAboutUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        return String.format("Пользователь: userName %s, role %s", user.getUsername(), user.getRole().name());
    }

    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional
    public User create(UserRequest request) {
        if (userRepository.existsUserByUsername(request.username())) {
            throw new IllegalArgumentException("Username уже существует");
        }
        User user = new User();
        user.setUsername(request.username());
        user.setPassword(encoder.encode(request.password()));
        user.setRole(request.role());
        user.setAccountNonLocked(true);
        user.setFailedAttempts(0);
        return userRepository.save(user);
    }

    @Transactional
    public void resetAttemptsAndUnlock(UUID userId) {
        User u = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        u.setFailedAttempts(0);
        u.setAccountNonLocked(true);
        userRepository.save(u);
    }

    @Transactional
    public void changeRole(UUID userId, String newRole) {
        if (StringUtils.isBlank(newRole)) {
            throw new IllegalArgumentException("Роль не может быть пустой");
        }
        Role role;
        try {
            role = Role.valueOf(newRole.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Недопустимая роль: " + newRole);
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        user.setRole(role);
        userRepository.save(user);
    }

    @Transactional
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }
}