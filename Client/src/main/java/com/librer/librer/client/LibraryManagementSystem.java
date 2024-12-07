package com.librer.librer.client;

import javafx.application.Application;
import javafx.collections.ObservableList;
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

    // Declare the userId globally so it can be accessed across the class
    private int userId;

    @Override
    public void start(Stage primaryStage) {
        // Title and setup
        Label title = new Label("Library Management System");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2e86c1;");

        // Custom title
        Label customLabel = new Label("Welcome to the Library");
        customLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #117a65;");

        // Input fields with maximum width
        TextField userNameField = new TextField();
        userNameField.setPromptText("Name");
        userNameField.setMaxWidth(200);

        PasswordField userPasswordField = new PasswordField();
        userPasswordField.setPromptText("Password");
        userPasswordField.setMaxWidth(200);

        // Buttons
        Button userLoginButton = new Button("Login");
        userLoginButton.setStyle("-fx-font-size: 14px; -fx-background-color: #117a65; -fx-text-fill: white; -fx-pref-width: 100px;");
        Button userRegisterButton = new Button("Register");
        userRegisterButton.setStyle("-fx-font-size: 12px; -fx-background-color: #2e86c1; -fx-text-fill: white; -fx-pref-width: 80px;");

        // Action for register button
        userRegisterButton.setOnAction(e -> {
            Stage registrationStage = new Stage();
            Registration registration = new Registration();
            registration.start(registrationStage); // Open registration window
        });

        // Layout for user text fields and buttons
        VBox userBox = new VBox(10, customLabel, userNameField, userPasswordField, userLoginButton, userRegisterButton);
        userBox.setAlignment(Pos.TOP_CENTER);
        userBox.setPadding(new Insets(20));

        // Action for login button
        userLoginButton.setOnAction(e -> {
            String userName = userNameField.getText();
            String password = userPasswordField.getText();

            // Authenticate using UsersFetcher
            UsersFetcher usersFetcher = new UsersFetcher();
            ObservableList<UsersFetcher.User> users = usersFetcher.fetchData();
            boolean isAuthenticated = false;
            int roleId = -1;

            // Check if entered credentials match any user
            for (UsersFetcher.User user : users) {
                if (user.getUserName().equals(userName) && user.getPassword().equals(password)) {
                    isAuthenticated = true;
                    userId = user.getUserId(); // Set the userId globally
                    roleId = user.getRoleId();
                    break;
                }
            }

            // Highlight fields based on incorrect input
            if (!isAuthenticated) {
                if (!users.stream().anyMatch(u -> u.getUserName().equals(userName))) {
                    userNameField.setStyle("-fx-border-color: red;");
                } else {
                    userNameField.setStyle("");
                }

                if (!users.stream().anyMatch(u -> u.getPassword().equals(password))) {
                    userPasswordField.setStyle("-fx-border-color: red;");
                } else {
                    userPasswordField.setStyle("");
                }
            } else {
                // Reset styles on successful login
                userNameField.setStyle("");
                userPasswordField.setStyle("");

                // Print userId to the console
                System.out.println("User ID: " + userId); // Print the userId

                if (roleId == 1) {
                    // Navigate to Admin Page
                    AdministratorPage adminPage = new AdministratorPage(userName, primaryStage);
                    primaryStage.setTitle("Administrator Page");
                } else {
                    // Navigate to User Page
                    UserPage userPage = new UserPage(userName, primaryStage, userId);  // Pass userId here
                    primaryStage.setTitle("User Page");
                }
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





