package com.anastasiia.itkacademy.entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@Entity
@Table(name = "products")
@SQLRestriction("deleted = false")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    @NotNull(message = "Название обязательно для заполнения")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    @NotNull(message = "Цена обязательна для заполнения")
    @PositiveOrZero
    private BigDecimal price;

    @Column(name = "quantity_stock", nullable = false)
    @NotNull(message = "Количество обязательно для заполнения")
    @PositiveOrZero
    private Integer quantityStock;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}