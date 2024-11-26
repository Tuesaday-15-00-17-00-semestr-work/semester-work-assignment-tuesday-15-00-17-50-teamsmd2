package com.librer.Librer.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.asm.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

@Component
public class BookJsonDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(BookJsonDataLoader.class);
    private final BookRepository bookRepository;
    private final ObjectMapper objectMapper;

    public BookJsonDataLoader(BookRepository bookRepository, ObjectMapper objectMapper) {
        this.bookRepository = bookRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
//        createDatabase();
        if(bookRepository.count() == 0) {
            try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/books.json")) {
                Books allBooks = objectMapper.readValue(inputStream, Books.class);
                log.info("Saving books...", allBooks.books().size());
                bookRepository.saveAll(allBooks.books());
            } catch (IOException e) {
                throw new RuntimeException("Failed to load data from file", e);
            }
        } else {
            log.info("Books already loaded");
        }
    }
}

