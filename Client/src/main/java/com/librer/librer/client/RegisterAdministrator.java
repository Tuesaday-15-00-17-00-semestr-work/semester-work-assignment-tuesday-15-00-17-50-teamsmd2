package com.librer.librer.client;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class RegisterAdministrator {

    public void show() {
        Stage stage = new Stage();
        stage.setTitle("Register Administrator");

        // Labels and text fields for registration details
        Label titleLabel = new Label("Register Administrator");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        emailField.setPromptText("Enter email");

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter name");

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");

        // Buttons
        Button registerButton = new Button("Register");
        registerButton.setOnAction(e -> {
            // Register logic here
            System.out.println("Administrator Registered: " + nameField.getText());
            stage.close(); // Close the window after registration
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> stage.close());

        // Layout setup
        VBox inputBox = new VBox(10, emailLabel, emailField, nameLabel, nameField, passwordLabel, passwordField, registerButton);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setStyle("-fx-padding: 20px;");

        HBox buttonBox = new HBox(10, cancelButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setPadding(new Insets(10));

        // Main layout
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        root.setCenter(inputBox);
        root.setBottom(buttonBox);
        BorderPane.setAlignment(buttonBox, Pos.BOTTOM_RIGHT);

        // Scene setup with larger window size
        Scene scene = new Scene(root, 300, 350);  // Increased height from 250 to 400
        stage.setScene(scene);
        stage.show();
    }
}


