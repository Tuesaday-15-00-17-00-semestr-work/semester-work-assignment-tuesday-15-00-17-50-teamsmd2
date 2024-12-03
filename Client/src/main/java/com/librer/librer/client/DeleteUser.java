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


public class DeleteUser {
    public void start(Stage parentStage) {
        Stage deleteUserStage = new Stage();
        deleteUserStage.setTitle("Delete User");

        // Label and text field for User ID
        Label userIdLabel = new Label("User ID:");
        TextField userIdField = new TextField();

        // Buttons
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            String userId = userIdField.getText();

            // Perform delete logic here (e.g., remove user from database or list)
            System.out.println("User Deleted: ID=" + userId);

            deleteUserStage.close(); // Close the window after deleting
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> deleteUserStage.close());

        // Layout setup
        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                userIdLabel, userIdField,
                deleteButton
        );
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20px;");

        // Place Cancel button at the bottom-right
        HBox cancelBox = new HBox(cancelButton);
        cancelBox.setAlignment(Pos.BOTTOM_RIGHT);
        cancelBox.setPadding(new Insets(10));

        layout.getChildren().add(cancelBox);

        Scene scene = new Scene(layout, 300, 150);
        deleteUserStage.setScene(scene);
        deleteUserStage.show();
    }
}
