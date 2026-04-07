package com.anastasiia.itkacademy.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToMany
    @JoinTable(
            name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"
            ))
    private List<Product> products;

    @Column(name = "order_date", nullable = false)
    @NotNull(message = "Дата обязательна для заполнения")
    private LocalDateTime orderDate;

    @Column(name = "shipping_address", nullable = false)
    @NotNull(message = "Адрес обязателен для заполнения")
    private String shippingAddress;

    @Column(name = "total_price", nullable = false)
    @NotNull(message = "Сумма заказа обязательна для заполнения")
    @PositiveOrZero
    private BigDecimal totalPrice;

    @Column(name = "order_status", nullable = false)
    @NotNull(message = "Статус заказа обязателен для заполнения")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}