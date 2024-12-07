package com.librer.librer.client;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javafx.scene.control.Alert;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class BorrowBook {
    private final HttpClient client = HttpClient.newHttpClient();
    private int userId;

    public BorrowBook(int userId) {
        this.userId = userId;
    }

    public void start(Stage parentStage, ObservableList<BooksFetcher.Book> availableBooks, AvailableBooks availableBooksPage) {
        Stage borrowStage = new Stage();
        borrowStage.setTitle("Borrow Book");

        Label bookIdLabel = new Label("Book ID:");
        TextField bookIdField = new TextField();

        Button borrowBookButton = new Button("Borrow");
        Button cancelButton = new Button("Cancel");

        cancelButton.setOnAction(e -> borrowStage.close());

        HBox buttonLayout = new HBox(10, borrowBookButton);
        buttonLayout.setAlignment(Pos.CENTER);

        HBox cancelLayout = new HBox(10, cancelButton);
        cancelLayout.setAlignment(Pos.BOTTOM_RIGHT);

        VBox borrowLayout = new VBox(10, bookIdLabel, bookIdField, buttonLayout);
        borrowLayout.setAlignment(Pos.CENTER);
        borrowLayout.setPadding(new Insets(20));
        borrowLayout.getChildren().add(cancelLayout);

        Scene borrowScene = new Scene(borrowLayout, 250, 200);
        borrowStage.setScene(borrowScene);
        borrowStage.show();

        borrowBookButton.setOnAction(e -> {
            if (validateInput(bookIdField, availableBooks)) {
                String bookId = bookIdField.getText();
                BooksFetcher.Book book = getBookDetails(bookId, availableBooks); // Retrieve book details
                if (book != null) {
                    MyBooks myBooks = new MyBooks(userId);
                    handleBorrowAction(book, myBooks, borrowStage, parentStage);
                } else {
                    showAlert("Error", "Book ID not found.");
                }
            }
        });
    }

    private BooksFetcher.Book getBookDetails(String bookId, ObservableList<BooksFetcher.Book> availableBooks) {
        for (BooksFetcher.Book book : availableBooks) {
            if (book.getBookId().equals(bookId)) {
                return book;
            }
        }
        return null;
    }

    private boolean validateInput(TextField bookIdField, ObservableList<BooksFetcher.Book> availableBooks) {
        String bookId = bookIdField.getText();
        bookIdField.setStyle("-fx-border-color: none;"); // Clear errors

        if (bookId.isEmpty()) {
            bookIdField.setStyle("-fx-border-color: red;");
            return false;
        }

        BooksFetcher.Book book = getBookDetails(bookId, availableBooks);
        if (book == null) {
            bookIdField.setStyle("-fx-border-color: red;");
            return false; // Invalid bookId
        }

        return true; // Valid input
    }

    private void handleBorrowAction(BooksFetcher.Book book, MyBooks myBooks, Stage borrowStage, Stage parentStage) {
        String requestBody = String.format(
                "[{\"transactionId\": %d, \"userId\": %d, \"bookId\": %s, \"action\": \"borrow\", \"date\": \"%s\"}]",
                getNextTransactionId(), userId, book.getBookId(), book.getTitle(), book.getAuthor(), book.getIsbn(), getCurrentDate()
        );

        String response = makeRequest("http://localhost:8080/api/transactions", "POST", requestBody);

        if (response.contains("Error")) {
            showAlert("Error", "Failed to borrow the book. Please try again.");
        } else {
            System.out.println("Book Borrowed: ID " + book.getBookId());
            myBooks.refreshBookList();

            borrowStage.close();

            MyBooks newMyBooks = new MyBooks(userId);
            newMyBooks.start(parentStage, "User Name");
        }
    }

    private String getCurrentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

    private int getNextTransactionId() {
        return 3; // Placeholder for real logic
    }

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
            return "Error: " + e.getMessage();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}





