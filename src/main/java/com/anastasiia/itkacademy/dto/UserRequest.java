package com.anastasiia.itkacademy.dto;

import com.anastasiia.itkacademy.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequest(@NotBlank String username,
                          @NotBlank String password,
                          @NotNull Role role) {}