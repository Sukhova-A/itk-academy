package com.anastasiia.itkacademy.service;

import com.anastasiia.itkacademy.dto.LoginRequest;
import com.anastasiia.itkacademy.dto.LoginResponse;
import com.anastasiia.itkacademy.entity.User;
import com.anastasiia.itkacademy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final OurUserDetailsService userDetailsService;
    private final JWTUtils jwtUtils;
    private final UserRepository userRepository;

    @Transactional(noRollbackFor = AuthenticationException.class)
    public LoginResponse authenticate(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователь %s не найден", request.getUsername())));

        if (!user.isAccountNonLocked()) {
            throw new RuntimeException("Пользователь заблокирован");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()));
            user.setFailedAttempts(0);
            userRepository.save(user);
        } catch (AuthenticationException ex) {
            user.setFailedAttempts(user.getFailedAttempts() + 1);
            if (user.getFailedAttempts() >= 5) {
                user.setAccountNonLocked(false);
            }
            userRepository.save(user);

            throw ex;
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String accessToken  = jwtUtils.generateToken(userDetails);
        String refreshToken = jwtUtils.generateRefreshToken(userDetails);

        return new LoginResponse(
                userDetails.getUsername(),
                user.getRole().name(),
                accessToken,
                refreshToken,
                JWTUtils.ACCESS_EXPIRATION_TIME);
    }
}