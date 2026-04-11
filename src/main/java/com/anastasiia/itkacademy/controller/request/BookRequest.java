package com.anastasiia.itkacademy.controller.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {

    @NotBlank(message = "Название не может быть пустым")
    @JsonProperty("title")
    private String title;

    @NotBlank(message = "Автор не может быть пустым")
    @JsonProperty("author")
    private String author;

    @NotNull(message = "Год публикации обязателен")
    @JsonProperty("publicationYear")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate publicationYear;
}