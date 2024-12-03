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
import javafx.scene.control.*;


public class EditUser {
    public void start(Stage parentStage) {
        Stage editUserStage = new Stage();
        editUserStage.setTitle("Edit User");

        // Labels and text fields for user details
        Label userIdLabel = new Label("User ID:");
        TextField userIdField = new TextField();

        Label userNameLabel = new Label("Name:");
        TextField userNameField = new TextField();

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Label passwordLabel = new Label("Password:");
        TextField passwordField = new TextField();

        // Role selection
        Label roleLabel = new Label("Role:");
        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("Admin", "User");
        roleComboBox.setValue("User"); // Default value

        // Buttons
        Button changeButton = new Button("Change");
        changeButton.setOnAction(e -> {
            String userId = userIdField.getText();
            String userName = userNameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String role = roleComboBox.getValue();

            // Perform update logic here (e.g., update the user in the database or list)
            System.out.println("User Updated: ID=" + userId + ", Name=" + userName + ", Email=" + email +
                    ", Password=" + password + ", Role=" + role);

            editUserStage.close(); // Close the window after saving changes
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> editUserStage.close());

        // Layout setup
        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                userIdLabel, userIdField,
                userNameLabel, userNameField,
                emailLabel, emailField,
                passwordLabel, passwordField,
                roleLabel, roleComboBox,
                changeButton
        );
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20px;");

        // Place Cancel button at the bottom-right
        HBox cancelBox = new HBox(cancelButton);
        cancelBox.setAlignment(Pos.BOTTOM_RIGHT);
        cancelBox.setPadding(new Insets(10));

        layout.getChildren().add(cancelBox);

        Scene scene = new Scene(layout, 300, 400);
        editUserStage.setScene(scene);
        editUserStage.show();
    }
}

