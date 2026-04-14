package com.anastasiia.itkacademy.controller;

import java.util.UUID;

import com.anastasiia.itkacademy.dto.RoleRequest;
import com.anastasiia.itkacademy.dto.UserRequest;
import com.anastasiia.itkacademy.entity.User;
import com.anastasiia.itkacademy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    @PreAuthorize("hasAnyAuthority('USER', 'MODERATOR', 'SUPER_ADMIN')")
    public ResponseEntity<String> getInfoAboutUser(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.getInfoAboutUser(userDetails.getUsername()));
    }

    @PostMapping("/all")
    @PreAuthorize("hasAnyAuthority('MODERATOR', 'SUPER_ADMIN')")
    public ResponseEntity<Page<User>> findAllUsers(Pageable pageable) {
        return ResponseEntity.ok(userService.findAll(pageable));
    }

    @PostMapping("/users/create")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
    public ResponseEntity<User> createUser(@RequestBody UserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(request));
    }

    @PutMapping("/users/{id}/reset")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
    public ResponseEntity<Void> resetAttempts(@PathVariable UUID id) {
        userService.resetAttemptsAndUnlock(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/users/{id}/role")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
    public ResponseEntity<Void> changeRole(@PathVariable UUID id,
                                           @RequestBody RoleRequest request) {
        userService.changeRole(id, request.value());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}