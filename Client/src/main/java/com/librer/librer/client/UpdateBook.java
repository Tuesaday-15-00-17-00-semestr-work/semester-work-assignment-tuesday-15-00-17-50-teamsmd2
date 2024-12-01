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


public class UpdateBook {
    public void start(Stage parentStage) {
        // Create the UpdateBook window (without closing the parent)
        Stage updateStage = new Stage();
        updateStage.setTitle("Update Book");

        // Book ID Field
        Label bookIdLabel = new Label("Book ID:");
        TextField bookIdField = new TextField();

        // Book Title Field
        Label titleLabel = new Label("Book Title:");
        TextField titleField = new TextField();

        // Author Field
        Label authorLabel = new Label("Author:");
        TextField authorField = new TextField();

        // ISBN Field
        Label isbnLabel = new Label("ISBN:");
        TextField isbnField = new TextField();

        // Available Copies Field
        Label copiesLabel = new Label("Available Copies:");
        TextField copiesField = new TextField();

        // Buttons for Update and Cancel
        Button updateButton = new Button("Update Book");
        updateButton.setOnAction(e -> {
            String bookId = bookIdField.getText();
            String title = titleField.getText();
            String author = authorField.getText();
            String isbn = isbnField.getText();
            String copies = copiesField.getText();

            // Here you would update the book details in your system
            System.out.println("Book Updated: [ID: " + bookId + "] " + title + " by " + author + " (ISBN: " + isbn + ")");
            updateStage.close(); // Close the "Update Book" window after updating the book
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> updateStage.close());

        // Layout
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
}

