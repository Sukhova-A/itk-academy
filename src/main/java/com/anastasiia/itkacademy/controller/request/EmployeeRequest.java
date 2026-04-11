package com.anastasiia.itkacademy.controller.request;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {

    @NotBlank
    @JsonProperty("firstName")
    private String firstName;

    @NotBlank
    @JsonProperty("lastName")
    private String lastName;

    @NotBlank
    @JsonProperty("position")
    private String position;

    @NotNull
    @PositiveOrZero
    @JsonProperty("salary")
    private BigDecimal salary;

    @NotNull
    @JsonProperty("departmentId")
    private UUID departmentId;
}