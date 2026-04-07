package com.anastasiia.itkacademy.controller.converter;

import com.anastasiia.itkacademy.controller.dto.CustomerDto;
import com.anastasiia.itkacademy.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDto toDto(Customer customer);
}