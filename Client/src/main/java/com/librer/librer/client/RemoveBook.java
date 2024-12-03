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


public class RemoveBook {
    public void start(Stage parentStage) {
        Stage removeStage = new Stage();
        removeStage.setTitle("Remove Book");

        Label bookIdLabel = new Label("Book ID:");
        TextField bookIdField = new TextField();

        Button removeButton = new Button("Remove Book");
        removeButton.setOnAction(e -> {
            String bookId = bookIdField.getText();
            System.out.println("Book ID: " + bookId + " removed.");
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
}

