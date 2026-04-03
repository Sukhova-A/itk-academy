package com.anastasiia.itkacademy.service;

import java.util.UUID;

import com.anastasiia.itkacademy.model.Author;
import com.anastasiia.itkacademy.repository.AuthorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Page<Author> findAll(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Author getAuthorById(UUID id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Автор с id %s не найден", id)));
    }

    public void deleteById(UUID id) {
        authorRepository.deleteById(id);
    }
}