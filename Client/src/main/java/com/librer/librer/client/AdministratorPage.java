package com.librer.librer.client;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class AdministratorPage {
    private String adminName;

    public AdministratorPage(String adminName, Stage primaryStage) {
        this.adminName = adminName;

        // Application title
        Label title = new Label("Library Management System");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Admin name
        Label adminNameLabel = new Label("Welcome admin, " + adminName);
        adminNameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        // Buttons for managing books and accounts
        Button manageBooksButton = new Button("Manage Books");
        Button manageAccountsButton = new Button("Manage Accounts");

        manageBooksButton.setStyle("-fx-font-size: 14px; -fx-pref-width: 150px;");
        manageAccountsButton.setStyle("-fx-font-size: 14px; -fx-pref-width: 150px;");

        // Logout button
        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-font-size: 14px;");

        logoutButton.setOnAction(e -> {
            LibraryManagementSystem librarySystem = new LibraryManagementSystem();
            librarySystem.start(primaryStage);  // Returns to the main window
        });

        manageBooksButton.setOnAction(e -> {
            ManageBooks manageBooks = new ManageBooks();
            manageBooks.start(primaryStage, adminName);  // Opens book management window with admin's name
        });

        manageAccountsButton.setOnAction(e -> {
            ManageAccounts manageAccounts = new ManageAccounts();
            manageAccounts.start(primaryStage, adminName);  // Fixed error: added adminName
        });

        // Layout for the title
        HBox headerBox = new HBox(20, title);
        headerBox.setAlignment(Pos.CENTER_LEFT);
        headerBox.setPadding(new Insets(20));

        // Layout for the logout button
        HBox logoutBox = new HBox(logoutButton);
        logoutBox.setAlignment(Pos.CENTER_RIGHT);
        logoutBox.setPadding(new Insets(20));

        // Layout for managing books and accounts
        VBox adminBox = new VBox(20, adminNameLabel, manageBooksButton, manageAccountsButton);
        adminBox.setAlignment(Pos.CENTER);
        adminBox.setPadding(new Insets(20));

        // Main layout for the admin page
        VBox root = new VBox(20, headerBox, adminBox, logoutBox);
        root.setPadding(new Insets(20));

        // Setting the scene for the administrator
        Scene adminScene = new Scene(root, 600, 400);
        primaryStage.setScene(adminScene);  // Sets the scene for Administrator
        primaryStage.setTitle("Administrator Page");  // Sets the window title
    }

    public void start(Stage primaryStage) {
        // This empty method is here for consistency but is not needed
    }
}





