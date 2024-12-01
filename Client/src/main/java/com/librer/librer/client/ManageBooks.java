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


public class ManageBooks {
    public void start(Stage primaryStage, String adminName) {
        Label titleLabel = new Label("Manage Books");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 14px;");
        backButton.setOnAction(e -> {
            AdministratorPage adminPage = new AdministratorPage(adminName, primaryStage);
            adminPage.start(primaryStage); // Returns to the administrator page
        });

        // Defining the book table
        TableView<Book> bookTable = new TableView<>();
        bookTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Book, String> bookIdColumn = new TableColumn<>("Book ID");
        bookIdColumn.setCellValueFactory(cellData -> cellData.getValue().bookIdProperty());

        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());

        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());

        TableColumn<Book, String> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setCellValueFactory(cellData -> cellData.getValue().isbnProperty());

        TableColumn<Book, String> availableCopiesColumn = new TableColumn<>("Available Copies");
        availableCopiesColumn.setCellValueFactory(cellData -> cellData.getValue().availableCopiesProperty());

        bookIdColumn.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.1));
        titleColumn.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.3));
        authorColumn.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.25));
        isbnColumn.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.2));
        availableCopiesColumn.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.15));

        bookTable.getColumns().addAll(bookIdColumn, titleColumn, authorColumn, isbnColumn, availableCopiesColumn);

        ObservableList<Book> books = FXCollections.observableArrayList(
                new Book("1", "Title 1", "Author 1", "ISBN1", "5"),
                new Book("2", "Title 2", "Author 2", "ISBN2", "3")
        );
        bookTable.setItems(books);

        Button borrowBookButton = new Button("Borrow Book");
        borrowBookButton.setStyle("-fx-font-size: 14px;");
        borrowBookButton.setOnAction(e -> {
            BorrowBook borrowBook = new BorrowBook();
            borrowBook.start(primaryStage); // Opens the book borrowing window
        });

        Button addBookButton = new Button("Add Book");
        addBookButton.setStyle("-fx-font-size: 14px;");
        addBookButton.setOnAction(e -> {
            AddBook addBook = new AddBook();
            Stage addBookStage = new Stage();
            addBook.start(addBookStage); // Opens the add book window
        });

        Button updateBookButton = new Button("Update Book");
        updateBookButton.setStyle("-fx-font-size: 14px;");
        updateBookButton.setOnAction(e -> {
            UpdateBook updateBook = new UpdateBook();
            Stage updateBookStage = new Stage();
            updateBook.start(updateBookStage); // Opens the update book window
        });

        Button removeBookButton = new Button("Remove Book");
        removeBookButton.setStyle("-fx-font-size: 14px;");
        removeBookButton.setOnAction(e -> {
            RemoveBook removeBook = new RemoveBook();
            removeBook.start(primaryStage); // Opens the remove book window
        });

        HBox buttonBox = new HBox(10, borrowBookButton, addBookButton, updateBookButton, removeBookButton, backButton);
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
        buttonBox.setPadding(new Insets(10, 10, 20, 10));

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_LEFT);
        layout.getChildren().addAll(titleLabel, bookTable, buttonBox);

        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Manage Books");
        primaryStage.show();
    }

    public static class Book {
        private final SimpleStringProperty bookId;
        private final SimpleStringProperty title;
        private final SimpleStringProperty author;
        private final SimpleStringProperty isbn;
        private final SimpleStringProperty availableCopies;

        public Book(String bookId, String title, String author, String isbn, String availableCopies) {
            this.bookId = new SimpleStringProperty(bookId);
            this.title = new SimpleStringProperty(title);
            this.author = new SimpleStringProperty(author);
            this.isbn = new SimpleStringProperty(isbn);
            this.availableCopies = new SimpleStringProperty(availableCopies);
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

        public SimpleStringProperty isbnProperty() {
            return isbn;
        }

        public SimpleStringProperty availableCopiesProperty() {
            return availableCopies;
        }
    }
}

