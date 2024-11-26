package com.librer.Librer;

import com.librer.Librer.book.Book;
import com.librer.Librer.book.BookRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication

public class LibrerApplication {

	private static final Logger log = LoggerFactory.getLogger(LibrerApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(LibrerApplication.class, args);
	}

//	@Bean
//	CommandLineRunner runner(BookRepository bookRepository) {
//		return args -> {
//			Book Book = new Book(1, "The Great Gatsby", "F. Scott Fitzgerald", "9780743273565", 5);
//			bookRepository.create(Book);
//		};
//	}

}
