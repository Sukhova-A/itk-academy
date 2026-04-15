package com.anastasiia.itkacademy.controller;

import com.anastasiia.itkacademy.entity.Role;
import com.anastasiia.itkacademy.entity.User;
import com.anastasiia.itkacademy.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class HomeController {

    private final UserRepository userRepository;

    @GetMapping("/")
    public String home() {
        return "This is Home page";
    }

    @GetMapping("/user-role")
    public String profile(@AuthenticationPrincipal OAuth2User principal) {
        User user = userRepository.findByLogin(principal.getName())
                .orElseThrow(() -> new EntityNotFoundException(String.format("User %s not found", principal.getName())));
        log.warn("Role request: {} - {}", user.getName(), user.getRole().name());
        return user.getRole().name();
    }

    @GetMapping("/admin")
    public String panel() {
        return "This page is only for admins";
    }

    @PostMapping("/role/{login}")
    public void setRole(@PathVariable String login,
                        @RequestParam Role role) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User %s not found", login)));
        user.setRole(role);
        userRepository.save(user);
        log.warn("Role changed: {} - {}", login, role);
    }

    @GetMapping("/logout/success")
    public String logoutInfo(Authentication auth) {
        log.info("Logout: {}", auth != null ? auth.getName() : "anonymous");
        return "Logged out locally";
    }
}