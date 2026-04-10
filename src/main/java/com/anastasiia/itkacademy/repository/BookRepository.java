package com.anastasiia.itkacademy.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.anastasiia.itkacademy.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepository {

    private final JdbcTemplate template;

    public BookRepository(JdbcTemplate jdbcTemplate) {
        this.template = jdbcTemplate;
    }

    private static final String INSERT_QUERY =
            "INSERT INTO books (title, author, publication_year) VALUES (?, ?, ?)";
    private static final String UPDATE_QUERY =
            "UPDATE books SET title = ?, author = ?, publication_year = ? WHERE id = ?";
    private static final String FIND_BY_ID_QUERY =
            "SELECT * FROM books WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY =
            "DELETE FROM books WHERE id = ?";

    private final RowMapper<Book> rowMapper = (rs, rowNum) -> {
        Book book = new Book();
        book.setId((UUID) rs.getObject("id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setPublicationYear(rs.getDate("publication_year").toLocalDate());
        return book;
    };

    public Book save(Book book) {
        var keyHolder = new GeneratedKeyHolder();
        template.update(con -> {
            PreparedStatement ps = con.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setDate(3, java.sql.Date.valueOf(book.getPublicationYear()));
            return ps;
        }, keyHolder);
        book.setId((UUID) keyHolder.getKeys().get("id"));
        return book;
    }

    public Book update(Book book) {
        template.update(UPDATE_QUERY,
                book.getTitle(),
                book.getAuthor(),
                java.sql.Date.valueOf(book.getPublicationYear()),
                book.getId());
        return book;
    }

    public Optional<Book> findById(UUID id) {
        return template.query(FIND_BY_ID_QUERY, rowMapper, id).stream().findFirst();
    }

    public void deleteById(UUID id) {
        template.update(DELETE_BY_ID_QUERY, id);
    }

    public Page<Book> findAll(Pageable pageable) {
        Integer total = template.queryForObject(
                "SELECT COUNT(*) FROM books",
                Integer.class);

        List<Book> content = template.query(
                "SELECT * FROM books ORDER BY id LIMIT ? OFFSET ?",
                rowMapper,
                pageable.getPageSize(),
                pageable.getOffset());

        return new PageImpl<>(content, pageable, total == null ? 0 : total);
    }
}