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


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;

public class AddBook {

    private final HttpClient client = HttpClient.newHttpClient();

    public void start(Stage parentStage, ManageBooks manageBooksPage) {
        Stage addStage = new Stage();
        addStage.setTitle("Add Book");

        Label titleLabel = new Label("Book Title:");
        TextField titleField = new TextField();

        Label authorLabel = new Label("Author:");
        TextField authorField = new TextField();

        Label isbnLabel = new Label("ISBN:");
        TextField isbnField = new TextField();

        Label copiesLabel = new Label("Number of Copies:");
        TextField copiesField = new TextField();

        Button addButton = new Button("Add Book");
        addButton.setOnAction(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            String isbn = isbnField.getText();
            String copies = copiesField.getText();

            // Clear previous styles
            titleField.setStyle("-fx-border-color: none;");
            authorField.setStyle("-fx-border-color: none;");
            isbnField.setStyle("-fx-border-color: none;");
            copiesField.setStyle("-fx-border-color: none;");

            boolean isValid = true;

            // Validation for required fields
            if (title.isEmpty()) {
                titleField.setStyle("-fx-border-color: red;");
                isValid = false;
            }
            if (author.isEmpty()) {
                authorField.setStyle("-fx-border-color: red;");
                isValid = false;
            }
            if (isbn.isEmpty()) {
                isbnField.setStyle("-fx-border-color: red;");
                isValid = false;
            }
            if (copies.isEmpty()) {
                copiesField.setStyle("-fx-border-color: red;");
                isValid = false;
            }

            if (!isValid) {
                System.out.println("All fields are required!");
                return; // Prevent proceeding if any field is empty
            }

            String requestBody = String.format("{\"title\": \"%s\", \"author\": \"%s\", \"isbn\": \"%s\", \"availableCopies\": %s}",
                    title, author, isbn, copies);
            String response = makeRequest("http://localhost:8080/api/books", "POST", requestBody);
            System.out.println("Book Added: " + title + " by " + author + " (ISBN: " + isbn + ")");
            addStage.close();

            // After adding the book, refresh the book list
            manageBooksPage.refreshBookList();
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> addStage.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(titleLabel, titleField, authorLabel, authorField, isbnLabel, isbnField, copiesLabel, copiesField, addButton);

        HBox cancelBox = new HBox(cancelButton);
        cancelBox.setAlignment(Pos.BOTTOM_RIGHT);
        cancelBox.setPadding(new Insets(10));

        layout.getChildren().add(cancelBox);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20px;");

        Scene scene = new Scene(layout, 300, 350);
        addStage.setScene(scene);
        addStage.show();
    }

    private String makeRequest(String url, String method, String body) {
        try {
            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(URI.create(url));

            if ("POST".equalsIgnoreCase(method) && body != null) {
                builder.POST(BodyPublishers.ofString(body))
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
}


