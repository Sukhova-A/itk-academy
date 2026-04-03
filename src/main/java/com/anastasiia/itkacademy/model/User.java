package com.anastasiia.itkacademy.model;

import java.util.List;
import java.util.UUID;

import com.anastasiia.itkacademy.controller.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Пользователь.
 */
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @JsonView(Views.UserSummary.class)
    private UUID id;

    @NotBlank(message = "Имя обязательно для заполнения")
    @Column(name = "name", nullable = false)
    @JsonView(Views.UserSummary.class)
    private String name;

    @Email(message = "Некорректный формат email")
    @Column(name = "mail", nullable = false)
    @JsonView(Views.UserDetails.class)
    private String mail;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView(Views.UserDetails.class)
    private List<Order> orders;
}