package com.librer.librer.client;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class UserPage {
    private String userName;

    public UserPage(String userName, Stage primaryStage) {
        this.userName = userName;

        // Title and user name
        Label title = new Label("Library Management System");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label userNameLabel = new Label("Welcome user, " + userName);
        userNameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        // Buttons for managing books
        Button myBooksButton = new Button("My Books");
        Button availableBooksButton = new Button("Available Books");

        myBooksButton.setStyle("-fx-font-size: 14px; -fx-pref-width: 150px;");
        availableBooksButton.setStyle("-fx-font-size: 14px; -fx-pref-width: 150px;");

        // Action for "My Books" button
        myBooksButton.setOnAction(e -> {
            MyBooks myBooks = new MyBooks();
            myBooks.start(primaryStage, userName); // Opens the MyBooks window
        });

        // Action for "Available Books" button
        availableBooksButton.setOnAction(e -> {
            AvailableBooks availableBooks = new AvailableBooks();
            availableBooks.start(primaryStage, userName); // Opens the AvailableBooks window
        });

        // Logout button
        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-font-size: 14px;");
        logoutButton.setOnAction(e -> {
            // Returns to LoginPage
            new LibraryManagementSystem().start(primaryStage);
        });

        // Layout for top section (Title, User Name)
        HBox headerBox = new HBox(20, title);
        headerBox.setAlignment(Pos.CENTER_LEFT);
        headerBox.setPadding(new Insets(20));

        // Layout for user actions
        VBox userBox = new VBox(20, userNameLabel, myBooksButton, availableBooksButton);
        userBox.setAlignment(Pos.CENTER);
        userBox.setPadding(new Insets(20));

        // Layout for logout button, to display it on the right
        HBox logoutBox = new HBox(logoutButton);
        logoutBox.setAlignment(Pos.CENTER_RIGHT);
        logoutBox.setPadding(new Insets(20, 20, 20, 20));  // Add padding to move the button to the right

        // Main layout for the user page
        VBox root = new VBox(20, headerBox, userBox, logoutBox);
        root.setPadding(new Insets(20));

        // Scene for the user page
        Scene userScene = new Scene(root, 600, 400);
        primaryStage.setScene(userScene);
        primaryStage.setTitle("User Page");  // Change window title
    }
}




