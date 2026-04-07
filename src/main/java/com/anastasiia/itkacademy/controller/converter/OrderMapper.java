package com.anastasiia.itkacademy.controller.converter;

import com.anastasiia.itkacademy.controller.dto.OrderDto;
import com.anastasiia.itkacademy.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "status",     expression = "java(order.getOrderStatus().name())")
    OrderDto toDto(Order order);
}