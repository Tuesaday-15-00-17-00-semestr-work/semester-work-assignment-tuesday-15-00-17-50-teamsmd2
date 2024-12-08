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


public class RemoveBook {
    private final BooksFetcher booksFetcher = new BooksFetcher();
    private final ManageBooks manageBooks;

    public RemoveBook(ManageBooks manageBooks) {
        this.manageBooks = manageBooks;
    }

    public void start(Stage parentStage) {
        Stage removeStage = new Stage();
        removeStage.setTitle("Remove Book");

        Label bookIdLabel = new Label("Book ID:");
        TextField bookIdField = new TextField();

        Button removeButton = new Button("Remove Book");
        removeButton.setOnAction(e -> {
            String bookId = bookIdField.getText();

            // Validation for required field
            if (bookId.isEmpty()) {
                System.out.println("Book ID is required!");
                bookIdField.setStyle("-fx-border-color: red;");
                return; // Prevent proceeding if the field is empty
            }

            // Call API or method to remove the book using bookId
            String response = makeRequest("http://localhost:8080/api/books/" + bookId, "DELETE", null);
            System.out.println("Book ID: " + bookId + " removed.");
            manageBooks.refreshBookList();  // Refresh the book list in ManageBooks
            removeStage.close();  // Close the window after removing the book
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> removeStage.close());

        // Layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(bookIdLabel, bookIdField, removeButton);

        // Place Cancel button at the bottom-right
        HBox cancelBox = new HBox(cancelButton);
        cancelBox.setAlignment(Pos.BOTTOM_RIGHT);
        cancelBox.setPadding(new Insets(10));

        layout.getChildren().add(cancelBox);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20px;");

        Scene scene = new Scene(layout, 300, 150);
        removeStage.setScene(scene);
        removeStage.show();
    }

    private String makeRequest(String url, String method, String body) {
        try {
            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(URI.create(url));

            if ("DELETE".equalsIgnoreCase(method)) {
                builder.DELETE();
            }

            HttpRequest request = builder.build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}


