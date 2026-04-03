package com.anastasiia.itkacademy.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @NotBlank(message = "Заголовок обязателен для заполнения")
    private String title;

    @Column(nullable = false)
    @Min(value = 0, message = "Год публикации не может быть отрицательным")
    @NotNull(message = "Год публикации обязателен для заполнения")
    private int publicationYear;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @NotNull(message = "Автор обязателен для заполнения")
    private Author author;
}