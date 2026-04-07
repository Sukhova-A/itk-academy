package com.anastasiia.itkacademy.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("customerId")
    private UUID customerId;

    @JsonProperty("products")
    private List<ProductDto> products;

    @JsonProperty("orderDate")
    private LocalDateTime orderDate;

    @JsonProperty("shippingAddress")
    private String shippingAddress;

    @JsonProperty("totalPrice")
    private BigDecimal totalPrice;

    @JsonProperty("status")
    private String status;
}