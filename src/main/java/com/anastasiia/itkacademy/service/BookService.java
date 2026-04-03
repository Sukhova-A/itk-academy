package com.anastasiia.itkacademy.service;

import java.util.UUID;

import com.anastasiia.itkacademy.model.Author;
import com.anastasiia.itkacademy.model.Book;
import com.anastasiia.itkacademy.repository.AuthorRepository;
import com.anastasiia.itkacademy.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;

    public BookService(BookRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Book getBookById(UUID id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Книга с id %s не найдена", id)));
    }

    public Book saveBook(Book book) {
        Author author = book.getAuthor();
        if (author.getId() == null) {
            author = authorService.saveAuthor(author);
            book.setAuthor(author);
        }
        return bookRepository.save(book);
    }

    public Book updateBook(UUID id, Book bookDetails) {
        Book book = getBookById(id);
        book.setTitle(bookDetails.getTitle());
        book.setPublicationYear(bookDetails.getPublicationYear());
        book.setAuthor(bookDetails.getAuthor());
        return bookRepository.save(book);
    }

    public void deleteBook(UUID id) {
        bookRepository.deleteById(id);
    }
}