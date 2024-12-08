package com.librer.librer.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class BooksFetcher {
    private final HttpClient client = HttpClient.newHttpClient();
    private ObservableList<Book> books = FXCollections.observableArrayList();

    // Constructor
    public BooksFetcher() {
    }

    // Fetch the data and return the list of books
    public ObservableList<Book> fetchData() {
        String response = makeRequest("http://localhost:8080/api/books", "GET", null);
        parseBooksResponse(response);
        return books;
    }

    // Method to make an HTTP request
    private String makeRequest(String url, String method, String body) {
        try {
            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(URI.create(url));

            if ("POST".equalsIgnoreCase(method) && body != null) {
                builder.POST(HttpRequest.BodyPublishers.ofString(body))
                        .header("Content-Type", "application/json");
            } else {
                builder.GET();
            }

            HttpRequest request = builder.build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    // Method to parse the JSON response from the server
    private void parseBooksResponse(String response) {
        try {
            // Remove the surrounding brackets
            response = response.replaceAll("^\\[|\\]$", "");

            String[] bookEntries = response.split("},\\{");
            for (String entry : bookEntries) {
                entry = entry.replaceAll("^\\{|\\}$", ""); // Clean the braces around the entry

                String[] fields = entry.split(",");
                String bookId = fields[0].split(":")[1].replace("\"", "").trim();
                String title = fields[1].split(":")[1].replace("\"", "").trim();
                String author = fields[2].split(":")[1].replace("\"", "").trim();
                String isbn = fields[3].split(":")[1].replace("\"", "").trim();
                String availableCopies = fields[4].split(":")[1].replace("\"", "").trim();

                books.add(new Book(bookId, title, author, isbn, availableCopies));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to fetch a specific book by its ID
    public Book fetchBookById(int bookId) {
        for (Book book : books) {
            if (Integer.parseInt(book.getBookId()) == bookId) {
                return book; // Return the matching book
            }
        }
        return null; // Return null if no matching book is found
    }

    // Book class to store the individual book data
    public static class Book {
        private final String bookId;
        private final String title;
        private final String author;
        private final String isbn;
        private final String availableCopies;

        public Book(String bookId, String title, String author, String isbn, String availableCopies) {
            this.bookId = bookId;
            this.title = title;
            this.author = author;
            this.isbn = isbn;
            this.availableCopies = availableCopies;
        }

        // Getters for book properties
        public String getBookId() {
            return bookId;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public String getIsbn() {
            return isbn;
        }

        public String getAvailableCopies() {
            return availableCopies;
        }
    }
}


