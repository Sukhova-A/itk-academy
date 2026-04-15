package com.anastasiia.itkacademy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    private String id;
    private String login;
    private String name;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;
}