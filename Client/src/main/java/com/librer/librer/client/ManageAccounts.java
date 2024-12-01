package com.librer.librer.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.*;


public class ManageAccounts {
    private String adminName;

    public void start(Stage primaryStage, String adminName) {
        this.adminName = adminName;

        Label titleLabel = new Label("Manage Accounts");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 14px;");
        backButton.setOnAction(e -> {
            AdministratorPage adminPage = new AdministratorPage(adminName, primaryStage);  // Odovzdá meno pri návrate
            adminPage.start(primaryStage);
        });

        TableView<User> accountTable = new TableView<>();
        accountTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<User, String> userIdColumn = new TableColumn<>("User ID");
        userIdColumn.setCellValueFactory(cellData -> cellData.getValue().userIdProperty());

        TableColumn<User, String> userNameColumn = new TableColumn<>("User Name");
        userNameColumn.setCellValueFactory(cellData -> cellData.getValue().userNameProperty());

        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

        userIdColumn.prefWidthProperty().bind(accountTable.widthProperty().multiply(0.2));
        userNameColumn.prefWidthProperty().bind(accountTable.widthProperty().multiply(0.3));
        emailColumn.prefWidthProperty().bind(accountTable.widthProperty().multiply(0.5));

        accountTable.getColumns().addAll(userIdColumn, userNameColumn, emailColumn);

        ObservableList<User> users = FXCollections.observableArrayList(
                new User("1", "User One", "user1@example.com"),
                new User("2", "User Two", "user2@example.com")
        );

        accountTable.setItems(users);

        accountTable.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    User clickedUser = row.getItem();
                    openUserBooksWindow(clickedUser);
                }
            });
            return row;
        });

        Button addUserButton = new Button("Add User");
        addUserButton.setStyle("-fx-font-size: 14px;");
        addUserButton.setOnAction(e -> {
            RegisterUser registerUser = new RegisterUser();
            registerUser.show(); // Open Register User window
        });

        Button editUserButton = new Button("Edit User");
        editUserButton.setStyle("-fx-font-size: 14px;");
        editUserButton.setOnAction(e -> {
            EditUser editUser = new EditUser();
            Stage editUserStage = new Stage();
            editUser.start(editUserStage);
        });

        Button deleteUserButton = new Button("Delete User");
        deleteUserButton.setStyle("-fx-font-size: 14px;");
        deleteUserButton.setOnAction(e -> {
            DeleteUser deleteUser = new DeleteUser();
            Stage deleteUserStage = new Stage();
            deleteUser.start(deleteUserStage);
        });

        HBox buttonBox = new HBox(10, addUserButton, editUserButton, deleteUserButton, backButton);
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
        buttonBox.setPadding(new Insets(10, 10, 20, 10));

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_LEFT);
        layout.getChildren().addAll(titleLabel, accountTable, buttonBox);

        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Manage Accounts");
        primaryStage.show();
    }

    private void openUserBooksWindow(User user) {
        Stage stage = new Stage();
        stage.setTitle("Books Borrowed by " + user.getUserName());

        TableView<Book> bookTable = new TableView<>();
        bookTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Book, String> bookIdColumn = new TableColumn<>("Book ID");
        bookIdColumn.setCellValueFactory(cellData -> cellData.getValue().bookIdProperty());

        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());

        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());

        TableColumn<Book, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        TableColumn<Book, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        bookTable.getColumns().addAll(bookIdColumn, titleColumn, authorColumn, statusColumn, dateColumn);

        ObservableList<Book> books = FXCollections.observableArrayList(
                new Book("101", "Book One", "Author One", "Borrowed", "2024-01-10"),
                new Book("102", "Book Two", "Author Two", "Returned", "2024-01-15")
        );

        bookTable.setItems(books);

        Button closeButton = new Button("Close");
        closeButton.setStyle("-fx-font-size: 14px;");
        closeButton.setOnAction(e -> stage.close());

        HBox buttonBox = new HBox(10, closeButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setPadding(new Insets(10));

        VBox layout = new VBox(20, bookTable, buttonBox);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 500, 300);
        stage.setScene(scene);
        stage.show();
    }

    public static class User {
        private final SimpleStringProperty userId;
        private final SimpleStringProperty userName;
        private final SimpleStringProperty email;

        public User(String userId, String userName, String email) {
            this.userId = new SimpleStringProperty(userId);
            this.userName = new SimpleStringProperty(userName);
            this.email = new SimpleStringProperty(email);
        }

        public String getUserName() {
            return userName.get();
        }

        public SimpleStringProperty userIdProperty() {
            return userId;
        }

        public SimpleStringProperty userNameProperty() {
            return userName;
        }

        public SimpleStringProperty emailProperty() {
            return email;
        }
    }

    public static class Book {
        private final SimpleStringProperty bookId;
        private final SimpleStringProperty title;
        private final SimpleStringProperty author;
        private final SimpleStringProperty status;
        private final SimpleStringProperty date;

        public Book(String bookId, String title, String author, String status, String date) {
            this.bookId = new SimpleStringProperty(bookId);
            this.title = new SimpleStringProperty(title);
            this.author = new SimpleStringProperty(author);
            this.status = new SimpleStringProperty(status);
            this.date = new SimpleStringProperty(date);
        }

        public SimpleStringProperty bookIdProperty() {
            return bookId;
        }

        public SimpleStringProperty titleProperty() {
            return title;
        }

        public SimpleStringProperty authorProperty() {
            return author;
        }

        public SimpleStringProperty statusProperty() {
            return status;
        }

        public SimpleStringProperty dateProperty() {
            return date;
        }
    }
}




