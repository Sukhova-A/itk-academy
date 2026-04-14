package com.anastasiia.itkacademy.dto;

import jakarta.validation.constraints.NotBlank;

public record RoleRequest(@NotBlank String value) {}