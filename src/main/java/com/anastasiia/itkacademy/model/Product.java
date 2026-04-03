package com.anastasiia.itkacademy.model;

import java.math.BigInteger;
import java.util.UUID;

import com.anastasiia.itkacademy.controller.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Продукт.
 */
@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {

    @Id
    @JsonView(Views.UserDetails.class)
    private UUID uuid;

    @Column(name = "name", nullable = false)
    @JsonView(Views.UserDetails.class)
    private String name;

    @Column(name = "price", nullable = false)
    @JsonView(Views.UserDetails.class)
    private BigInteger price;
}