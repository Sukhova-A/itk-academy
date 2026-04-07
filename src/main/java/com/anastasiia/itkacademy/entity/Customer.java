package com.anastasiia.itkacademy.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "first_name", nullable = false)
    @NotNull(message = "Имя обязательно для заполнения")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotNull(message = "Фамилия обязательна для заполнения")
    private String lastName;

    @Email(message = "Некорректный формат email")
    @Column(name = "email", nullable = false)
    @NotNull(message = "")
    private String email;

    @Column(name = "number", length = 25)
    private String number;
}