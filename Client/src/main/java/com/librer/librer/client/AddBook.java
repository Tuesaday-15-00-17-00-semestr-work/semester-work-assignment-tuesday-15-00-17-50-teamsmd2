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


public class AddBook {
    public void start(Stage parentStage) {
        // Create the AddBook window (without closing the parent)
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

            // Here you would add the book to your system
            System.out.println("Book Added: " + title + " by " + author + " (ISBN: " + isbn + ")");
            addStage.close();  // Close the "Add Book" window after adding the book
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> addStage.close());

        // Layout setup
        VBox layout = new VBox(10);
        layout.getChildren().addAll(titleLabel, titleField, authorLabel, authorField, isbnLabel, isbnField, copiesLabel, copiesField, addButton);

        // Place Cancel button at the bottom-right
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
}
