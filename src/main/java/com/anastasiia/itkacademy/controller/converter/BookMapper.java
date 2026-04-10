package com.anastasiia.itkacademy.controller.converter;

import com.anastasiia.itkacademy.controller.dto.BookDto;
import com.anastasiia.itkacademy.entity.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface  BookMapper {

    BookDto toDto(Book book);
}