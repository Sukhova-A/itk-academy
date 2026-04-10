package com.anastasiia.itkacademy.entity;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("books")
public class Book {

    @Id
    private UUID id;
    private String title;
    private String author;
    private LocalDate publicationYear;
}