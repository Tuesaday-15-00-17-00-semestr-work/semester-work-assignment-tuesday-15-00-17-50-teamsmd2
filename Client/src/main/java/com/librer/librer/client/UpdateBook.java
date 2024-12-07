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
import java.util.HashMap;
import java.util.Map;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.stage.Stage;
import java.net.URI;
import java.net.http.*;
import java.util.*;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.stage.Stage;
import java.net.URI;
import java.net.http.*;
import java.util.*;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.stage.Stage;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Platform;
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
import java.util.HashMap;
import java.util.Map;

public class UpdateBook {
    private final ManageBooks manageBooks;

    public UpdateBook(ManageBooks manageBooks) {
        this.manageBooks = manageBooks;
    }

    public void start(Stage parentStage, BooksFetcher.Book bookToEdit) {
        // Create the UpdateBook window
        Stage updateStage = new Stage();
        updateStage.setTitle("Update Book");

        // Create labels and fields for each input
        Label bookIdLabel = new Label("Book ID:");
        TextField bookIdField = new TextField(String.valueOf(bookToEdit.getBookId()));
        bookIdField.setEditable(false);  // Disable editing for bookId

        Label titleLabel = new Label("Book Title:");
        TextField titleField = new TextField(bookToEdit.getTitle());

        Label authorLabel = new Label("Author:");
        TextField authorField = new TextField(bookToEdit.getAuthor());

        Label isbnLabel = new Label("ISBN:");
        TextField isbnField = new TextField(bookToEdit.getIsbn());

        Label copiesLabel = new Label("Available Copies:");
        TextField copiesField = new TextField(String.valueOf(bookToEdit.getAvailableCopies()));

        // Button to update the book
        Button updateButton = new Button("Update Book");
        updateButton.setOnAction(e -> {
            String bookId = bookIdField.getText();
            String title = titleField.getText();
            String author = authorField.getText();
            String isbn = isbnField.getText();
            String copies = copiesField.getText();

            if (bookId.isEmpty()) {
                // Show an error message or highlight the field
                System.out.println("Book ID is required!");
                bookIdField.setStyle("-fx-border-color: red;"); // Highlight the field in red
                return; // Prevent the update if bookId is empty
            }

            // HTTP request to update the book
            String url = "http://localhost:8080/api/books/" + bookId;
            HttpClient client = HttpClient.newHttpClient();

            Map<String, String> updatedFields = new HashMap<>();
            if (!title.isEmpty()) updatedFields.put("title", title);
            if (!author.isEmpty()) updatedFields.put("author", author);
            if (!isbn.isEmpty()) updatedFields.put("isbn", isbn);
            if (!copies.isEmpty()) updatedFields.put("availableCopies", copies);

            String jsonBody = createJsonBody(updatedFields);

            // Build HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            // Send the request asynchronously
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> {
                        if (response.statusCode() == 200) {
                            System.out.println("Book Updated: [ID: " + bookId + "]");
                        } else {
                            System.out.println("Failed to update book. Response: " + response.body());
                        }
                        // Refresh the book list before closing the window
                        manageBooks.refreshBookList();
                        // Ensure window closes on JavaFX Application Thread
                        Platform.runLater(updateStage::close);
                    }).exceptionally(ex -> {
                        System.out.println("Error: " + ex.getMessage());
                        // Refresh the book list even if there's an error
                        manageBooks.refreshBookList();
                        // Ensure window closes on JavaFX Application Thread
                        Platform.runLater(updateStage::close);
                        return null;
                    });
        });

        // Cancel button
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> updateStage.close());

        // Layout setup
        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                bookIdLabel, bookIdField,
                titleLabel, titleField,
                authorLabel, authorField,
                isbnLabel, isbnField,
                copiesLabel, copiesField,
                updateButton
        );

        // Place Cancel button at the bottom-right
        HBox cancelBox = new HBox(cancelButton);
        cancelBox.setAlignment(Pos.BOTTOM_RIGHT);
        cancelBox.setPadding(new Insets(10));

        layout.getChildren().add(cancelBox);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20px;");

        // Scene
        Scene scene = new Scene(layout, 300, 400);
        updateStage.setScene(scene);
        updateStage.show();
    }

    // Helper method to create JSON body
    private String createJsonBody(Map<String, String> fields) {
        StringBuilder jsonBody = new StringBuilder("{");
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            jsonBody.append("\"").append(entry.getKey()).append("\":\"")
                    .append(entry.getValue()).append("\",");
        }
        if (jsonBody.length() > 1) {
            jsonBody.setLength(jsonBody.length() - 1);
        }
        jsonBody.append("}");
        return jsonBody.toString();
    }
}

