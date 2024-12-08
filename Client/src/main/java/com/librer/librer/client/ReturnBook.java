package com.librer.librer.client;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javafx.scene.control.Alert;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ReturnBook {
    private final HttpClient client = HttpClient.newHttpClient();

    public void start(Stage parentStage, MyBooks myBooks, int userId) {
        Stage returnStage = new Stage();
        returnStage.setTitle("Return Book");

        // UI elements
        Label bookIdLabel = new Label("Book ID:");
        TextField bookIdField = new TextField();

        Button returnBookButton = new Button("Return");
        Button cancelButton = new Button("Cancel");

        returnBookButton.setOnAction(e -> {
            // Validation first
            if (!validateInput(bookIdField, myBooks)) {
                return; // Stop if input is invalid
            }

            handleReturnAction(bookIdField.getText(), myBooks, userId, parentStage, returnStage);
        });

        cancelButton.setOnAction(e -> returnStage.close());

        // Button layout
        HBox buttonLayout = new HBox(10, returnBookButton);
        buttonLayout.setAlignment(Pos.CENTER);

        // Cancel button layout
        HBox cancelLayout = new HBox(10, cancelButton);
        cancelLayout.setAlignment(Pos.BOTTOM_RIGHT);

        VBox returnLayout = new VBox(10, bookIdLabel, bookIdField, buttonLayout);
        returnLayout.setAlignment(Pos.CENTER);
        returnLayout.setPadding(new Insets(20));
        returnLayout.getChildren().add(cancelLayout);

        Scene returnScene = new Scene(returnLayout, 250, 200);
        returnStage.setScene(returnScene);
        returnStage.show();
    }

    private boolean validateInput(TextField bookIdField, MyBooks myBooks) {
        String bookId = bookIdField.getText();
        bookIdField.setStyle("-fx-border-color: none;"); // Clear errors

        if (bookId.isEmpty()) {
            bookIdField.setStyle("-fx-border-color: red;");
            return false;
        }

        try {
            int bookIdInt = Integer.parseInt(bookId);
            if (!myBooks.getDisplayedBookIds().contains(bookIdInt)) {
                bookIdField.setStyle("-fx-border-color: red;");
                return false;
            }
        } catch (NumberFormatException ex) {
            bookIdField.setStyle("-fx-border-color: red;");
            return false;
        }

        return true; // Valid input
    }

    private void handleReturnAction(String bookId, MyBooks myBooks, int userId, Stage parentStage, Stage returnStage) {
        String requestBody = String.format(
                "{\"transactionId\": %d, \"userId\": %d, \"bookId\": \"%s\", \"action\": \"Returned\", \"date\": \"%s\"}",
                getNextTransactionId(), userId, bookId, getCurrentDate()
        );

        String response = makeRequest("http://localhost:8080/api/transactions", "POST", requestBody);

        if (response.contains("Error")) {
            showAlert("Error", "Failed to return the book. Please try again.");
        } else {
            System.out.println("Book Returned: ID " + bookId);
            myBooks.removeReturnedBook(Integer.parseInt(bookId)); // Remove the returned book from the displayed list
            myBooks.refreshBookList(); // Refresh the book list

            returnStage.close();

            // Create and show a new MyBooks window
            MyBooks newMyBooks = new MyBooks(userId);
            newMyBooks.start(parentStage, "User Name"); // Pass the user name if needed
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




