package com.anastasiia.itkacademy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

    private String username;
    private String role;
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
}