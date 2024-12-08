package com.librer.Librer.book;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import java.util.List;
import java.util.Optional;


@Repository
public class BookRepository {

    private static final Logger log = LoggerFactory.getLogger(BookRepository.class);
    private final JdbcClient jdbcClient;

    public BookRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Book> findAll() {
        return jdbcClient.sql("SELECT * FROM books")
                .query(Book.class)
                .list();
    }

    public Optional<Book> findById(int id) {
        return jdbcClient.sql("SELECT id, title, author, isbn, availableCopies FROM books WHERE id = ?")
                .params(List.of(id))
                .query(Book.class)
                .optional();
    }

    public void create(Book book) {
        var updated = jdbcClient.sql("INSERT INTO books (title, author, isbn, availableCopies) VALUES (?, ?, ?, ?)")
                .params(List.of(book.title(), book.author(), book.isbn(), book.availableCopies()))
                .update();
        Assert.state(updated == 1, "Failed to insert Book" + book.title());
    }

    public void update(Book book, int id) {
        var updated = jdbcClient.sql("UPDATE books SET title = ?, author = ?, isbn = ?, availableCopies = ? WHERE id = ?")
                .params(List.of(book.title(), book.author(), book.isbn(), book.availableCopies(), id))
                .update();
        Assert.state(updated == 1, "Failed to update Book" + book.title());
    }

    // Set availableCopies to 0
    public void setAvailableCopiesToZero(int id) {
        var updated = jdbcClient.sql("UPDATE books SET availableCopies = ? WHERE id = ?")
                .params(List.of(0, id))
                .update();
        Assert.state(updated == 1, "Failed to set availableCopies to 0 for Book with id " + id);
    }

    public int count() {
        return jdbcClient.sql("SELECT * FROM books")
                .query().listOfRows().size();
    }

    public void saveAll(List<Book> books) {
        books.forEach(this::create);
    }

    public List<Book> findAllByAuthor(String author) {
        return jdbcClient.sql("SELECT * FROM books WHERE author LIKE ?")
                .params(List.of("%" + author + "%"))
                .query(Book.class)
                .list();
    }
}
