package com.anastasiia.itkacademy.repository;

import java.util.UUID;

import com.anastasiia.itkacademy.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
}