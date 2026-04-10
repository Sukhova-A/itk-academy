package com.anastasiia.itkacademy.service;

import java.util.UUID;

import com.anastasiia.itkacademy.controller.converter.BookMapper;
import com.anastasiia.itkacademy.controller.dto.BookDto;
import com.anastasiia.itkacademy.controller.request.BookRequest;
import com.anastasiia.itkacademy.entity.Book;
import com.anastasiia.itkacademy.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository,
                       BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Transactional
    public BookDto create(BookRequest request) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setPublicationYear(request.getPublicationYear());
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Transactional(readOnly = true)
    public BookDto getById(UUID id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Книга с id %s не найдена: ", id)));
        return bookMapper.toDto(book);
    }

    @Transactional
    public BookDto updateById(UUID id, BookRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Книга с id %s не найдена: ", id)));
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setPublicationYear(request.getPublicationYear());

        return bookMapper.toDto(bookRepository.update(book));
    }

    @Transactional
    public void delete(UUID id) {
        bookRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<BookDto> getAll(Pageable pageable) {
        return bookRepository.findAll(pageable).map(bookMapper::toDto);
    }
}