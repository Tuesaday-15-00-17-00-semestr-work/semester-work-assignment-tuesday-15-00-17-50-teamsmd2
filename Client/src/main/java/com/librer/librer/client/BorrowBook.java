package com.librer.librer.client;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.*;


public class BorrowBook {
    public void start(Stage parentStage) {
        Stage borrowStage = new Stage();
        borrowStage.setTitle("Borrow Book");

        Label bookIdLabel = new Label("Book ID:");
        TextField bookIdField = new TextField();

        Label userIdLabel = new Label("User ID:");
        TextField userIdField = new TextField();

        Button borrowButton = new Button("Borrow Book");
        borrowButton.setOnAction(e -> {
            String bookId = bookIdField.getText();
            String userId = userIdField.getText();
            System.out.println("Book ID: " + bookId + ", User ID: " + userId);
            borrowStage.close();  // Close the borrow window after borrowing
        });

        // Adding the user table
        TableView<User> accountTable = new TableView<>();
        accountTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<User, String> userIdColumn = new TableColumn<>("User ID");
        userIdColumn.setCellValueFactory(cellData -> cellData.getValue().userIdProperty());

        TableColumn<User, String> userNameColumn = new TableColumn<>("User Name");
        userNameColumn.setCellValueFactory(cellData -> cellData.getValue().userNameProperty());

        accountTable.getColumns().addAll(userIdColumn, userNameColumn);

        ObservableList<User> users = FXCollections.observableArrayList(
                new User("1", "User One"),
                new User("2", "User Two")
        );

        accountTable.setItems(users);

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> borrowStage.close());

        // Layout setup
        VBox layout = new VBox(10);
        layout.getChildren().addAll(bookIdLabel, bookIdField, userIdLabel, userIdField, borrowButton, accountTable);

        // Place Cancel button at the bottom-right
        HBox cancelBox = new HBox(cancelButton);
        cancelBox.setAlignment(Pos.BOTTOM_RIGHT);
        cancelBox.setPadding(new Insets(10));

        layout.getChildren().add(cancelBox);

        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20px;");

        Scene scene = new Scene(layout, 300, 400); // Adjusted to fit the table
        borrowStage.setScene(scene);
        borrowStage.show();
    }

    public static class User {
        private final SimpleStringProperty userId;
        private final SimpleStringProperty userName;

        public User(String userId, String userName) {
            this.userId = new SimpleStringProperty(userId);
            this.userName = new SimpleStringProperty(userName);
        }

        public SimpleStringProperty userIdProperty() {
            return userId;
        }

        public SimpleStringProperty userNameProperty() {
            return userName;
        }
    }
}
