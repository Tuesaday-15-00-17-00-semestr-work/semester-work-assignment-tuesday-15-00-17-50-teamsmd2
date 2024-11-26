package com.librer.Librer.book;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("")
    List<Book> findAll() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    Book findById(@PathVariable int id) {

        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new BookNotFoundException();
        }
        return book.get();
    }
    //create
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@Valid @RequestBody Book book) {
        bookRepository.create(book);
    }
    // put
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@Valid @RequestBody Book book, @PathVariable int id) {
        bookRepository.update(book, id);
    }

    // delete
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable int id) {
        bookRepository.delete(id);
    }

    @GetMapping("/search/{author}")
    List<Book> search(@PathVariable String author) {
        List<Book> books = bookRepository.findAllByAuthor(author);
        if (books.isEmpty()) {
            throw new BookNotFoundException();
        }
        return books;
    }

}