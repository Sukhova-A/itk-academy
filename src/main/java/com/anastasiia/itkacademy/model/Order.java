package com.anastasiia.itkacademy.model;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

import com.anastasiia.itkacademy.controller.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Заказ.
 */
@Getter
@Setter
@Entity
@Table(name = "user_order")
public class Order {

    @Id
    @JsonView(Views.UserDetails.class)
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonView(Views.UserDetails.class)
    private User user;


    @ManyToMany
    @JoinTable(
            name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @JsonView(Views.UserDetails.class)
    private List<Product> products;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @JsonView(Views.UserDetails.class)
    private OrderStatus status;

    @Column(name = "total_amount", nullable = false)
    @JsonView(Views.UserDetails.class)
    private BigInteger totalAmount;
}