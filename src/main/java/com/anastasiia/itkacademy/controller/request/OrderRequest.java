package com.anastasiia.itkacademy.controller.request;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.anastasiia.itkacademy.entity.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @NotNull(message = "Айди покупателя обязательно")
    @JsonProperty("customerId")
    private UUID customerId;

    @NotNull(message = "Список продуктов обязателен")
    @JsonProperty("productIds")
    private List<UUID> productsIds;

    @NotBlank(message = "Адрес не может быть пустым")
    @JsonProperty("shippingAddress")
    private String shippingAddress;

    @NotNull(message = "Цена обязательна")
    @JsonProperty("totalPrice")
    private BigDecimal totalPrice;

    @JsonProperty("status")
    private String status;

    public OrderStatus deriveStatus() {
        if (status == null || status.isBlank()) return OrderStatus.NEW;
        try {
            return OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Недопустимый статус: " + status);
        }
    }
}