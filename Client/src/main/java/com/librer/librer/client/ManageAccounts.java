package com.librer.librer.client;

import javafx.beans.property.SimpleIntegerProperty;
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
    private TableView<UsersFetcher.User> accountTable;

    public void start(Stage primaryStage, String adminName) {
        this.adminName = adminName;

        Label titleLabel = new Label("Manage Accounts");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 14px;");
        backButton.setOnAction(e -> {
            AdministratorPage adminPage = new AdministratorPage(adminName, primaryStage);
            adminPage.start(primaryStage);
        });

        accountTable = new TableView<>();
        accountTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<UsersFetcher.User, Integer> userIdColumn = new TableColumn<>("User ID");
        userIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getUserId()).asObject());

        TableColumn<UsersFetcher.User, String> userNameColumn = new TableColumn<>("User Name");
        userNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserName()));

        TableColumn<UsersFetcher.User, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));

        TableColumn<UsersFetcher.User, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(cellData -> {
            int roleId = cellData.getValue().getRoleId();
            return new SimpleStringProperty(roleId == 1 ? "admin" : "user");
        });

        userIdColumn.prefWidthProperty().bind(accountTable.widthProperty().multiply(0.2));
        userNameColumn.prefWidthProperty().bind(accountTable.widthProperty().multiply(0.3));
        emailColumn.prefWidthProperty().bind(accountTable.widthProperty().multiply(0.3));
        roleColumn.prefWidthProperty().bind(accountTable.widthProperty().multiply(0.2));

        accountTable.getColumns().addAll(userIdColumn, userNameColumn, emailColumn, roleColumn);

        UsersFetcher fetcher = new UsersFetcher();
        ObservableList<UsersFetcher.User> users = fetcher.fetchData();

        accountTable.setItems(users);

        accountTable.setRowFactory(tv -> {
            TableRow<UsersFetcher.User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    UsersFetcher.User clickedUser = row.getItem();
                    openUserBooksWindow(clickedUser);
                }
            });
            return row;
        });

        Button addUserButton = new Button("Add User");
        addUserButton.setStyle("-fx-font-size: 14px;");
        addUserButton.setOnAction(e -> {
            RegisterUser registerUser = new RegisterUser(this); // Pass ManageAccounts instance
            Stage registerUserStage = new Stage();
            registerUser.start(registerUserStage);
        });

        Button editUserButton = new Button("Edit User");
        editUserButton.setStyle("-fx-font-size: 14px;");
        editUserButton.setOnAction(e -> {
            // Get the selected user from the table
            UsersFetcher.User selectedUser = accountTable.getSelectionModel().getSelectedItem();

            // Check if a user is selected
            if (selectedUser != null) {
                // Open the EditUser window with the selected user's data
                EditUser editUser = new EditUser();
                Stage editUserStage = new Stage();
                editUser.start(editUserStage, selectedUser);
            } else {
                // Alert the user to select a user
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a user to edit.", ButtonType.OK);
                alert.showAndWait();
            }
        });

        Button deleteUserButton = new Button("Delete User");
        deleteUserButton.setStyle("-fx-font-size: 14px;");
        deleteUserButton.setOnAction(e -> {
            DeleteUser deleteUser = new DeleteUser(this);
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

    public void refreshTable() {
        UsersFetcher fetcher = new UsersFetcher();
        ObservableList<UsersFetcher.User> users = fetcher.fetchData();
        accountTable.setItems(users);
    }

    private void openUserBooksWindow(UsersFetcher.User user) {
        Stage stage = new Stage();
        stage.setTitle("Books Borrowed by " + user.getUserName());

        // Fetch books beforehand
        BooksFetcher booksFetcher = new BooksFetcher();
        ObservableList<BooksFetcher.Book> books = booksFetcher.fetchData(); // Fetch books once

        // Create table for books
        TableView<TransactionsFetcher.Transaction> bookTable = new TableView<>();
        bookTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Create columns
        TableColumn<TransactionsFetcher.Transaction, Integer> bookIdColumn = new TableColumn<>("Book ID");
        bookIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getBookId()).asObject());

        TableColumn<TransactionsFetcher.Transaction, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(cellData -> {
            int bookId = cellData.getValue().getBookId();
            BooksFetcher.Book book = findBookById(books, bookId);  // Use a helper method to find the book
            return new SimpleStringProperty(book != null ? book.getTitle() : "");
        });

        TableColumn<TransactionsFetcher.Transaction, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(cellData -> {
            int bookId = cellData.getValue().getBookId();
            BooksFetcher.Book book = findBookById(books, bookId);  // Use a helper method to find the book
            return new SimpleStringProperty(book != null ? book.getAuthor() : "");
        });

        TableColumn<TransactionsFetcher.Transaction, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAction()));

        TableColumn<TransactionsFetcher.Transaction, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));

        // Add columns to the table
        bookTable.getColumns().addAll(bookIdColumn, titleColumn, authorColumn, statusColumn, dateColumn);

        // Fetch transactions related to the user
        TransactionsFetcher transactionsFetcher = new TransactionsFetcher();
        transactionsFetcher.fetchData(); // Fetch all transactions
        ObservableList<TransactionsFetcher.Transaction> userTransactions = transactionsFetcher.getUserTransactions(user.getUserId());

        bookTable.setItems(userTransactions);

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

    // Helper method to find a book by ID
    private BooksFetcher.Book findBookById(ObservableList<BooksFetcher.Book> books, int bookId) {
        for (BooksFetcher.Book book : books) {
            if (Integer.parseInt(book.getBookId()) == bookId) {
                return book; // Return the matching book
            }
        }
        return null; // Return null if no matching book is found
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

        public String getBookId() {
            return bookId.get();
        }

        public String getTitle() {
            return title.get();
        }

        public String getAuthor() {
            return author.get();
        }

        public String getStatus() {
            return status.get();
        }

        public String getDate() {
            return date.get();
        }
    }
}

