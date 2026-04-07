package com.anastasiia.itkacademy.controller.converter;

import com.anastasiia.itkacademy.controller.dto.ProductDto;
import com.anastasiia.itkacademy.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toDto(Product entity);
}