package com.librer.librer.client;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;


public class LibraryManagementSystem extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Title
        Label title = new Label("Library Management System");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2e86c1;");

        // Custom title
        Label customLabel = new Label("Welcome to the Library");
        customLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #117a65;");

        // Text fields with maximum width
        TextField userNameField = new TextField();
        userNameField.setPromptText("Name");
        userNameField.setMaxWidth(200);

        PasswordField userPasswordField = new PasswordField();
        userPasswordField.setPromptText("Password");
        userPasswordField.setMaxWidth(200);

        // Buttons
        Button userLoginButton = new Button("Login");
        userLoginButton.setStyle("-fx-font-size: 14px; -fx-background-color: #5dade2; -fx-text-fill: white; -fx-pref-width: 100px;");
        Button userRegisterButton = new Button("Register");
        userRegisterButton.setStyle("-fx-font-size: 12px; -fx-background-color: #28b463; -fx-text-fill: white; -fx-pref-width: 80px;");
        userRegisterButton.setOnAction(e -> new RegisterUser().show());

        // Layout for user text fields and buttons
        VBox userBox = new VBox(10, customLabel, userNameField, userPasswordField, userLoginButton, userRegisterButton);
        userBox.setAlignment(Pos.TOP_CENTER);
        userBox.setPadding(new Insets(20));
        userBox.setStyle("-fx-background-color: #d5dbdb; -fx-border-radius: 10; -fx-padding: 20;");

        // Action for login button
        userLoginButton.setOnAction(e -> {
            String userName = userNameField.getText();
            if ("admin".equalsIgnoreCase(userName)) {
                AdministratorPage adminPage = new AdministratorPage(userName, primaryStage);
                primaryStage.setTitle("Administrator Page");
            } else {
                UserPage userPage = new UserPage(userName, primaryStage);
                primaryStage.setTitle("User Page");
            }
        });

        // Main layout
        VBox root = new VBox(20, title, userBox);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f2f3f4;");

        // Scene
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Library Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}






