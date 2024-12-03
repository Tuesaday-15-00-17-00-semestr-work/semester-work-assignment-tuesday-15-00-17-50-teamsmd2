package com.librer.librer.client;

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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class AvailableBooks {
    public void start(Stage primaryStage, String userName) {
        Label titleLabel = new Label("Available Books");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

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
        titleColumn.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.25));
        authorColumn.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.25));
        isbnColumn.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.2));
        availableCopiesColumn.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.2));

        bookTable.getColumns().addAll(bookIdColumn, titleColumn, authorColumn, isbnColumn, availableCopiesColumn);

        ObservableList<Book> books = FXCollections.observableArrayList(
                new Book("1", "Book Title 1", "Author 1", "ISBN1", "5"),
                new Book("2", "Book Title 2", "Author 2", "ISBN2", "3"),
                new Book("3", "Book Title 3", "Author 3", "ISBN3", "7")
        );

        bookTable.setItems(books);

        Button addBookButton = new Button("Add Book");
        addBookButton.setStyle("-fx-font-size: 14px;");
        addBookButton.setOnAction(e -> {
            AddBook addBook = new AddBook();
            addBook.start(primaryStage); // Otvorí okno na pridávanie kníh
        });

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 14px;");
        backButton.setOnAction(e -> {
            UserPage userPage = new UserPage(userName, primaryStage);
            //userPage.start(primaryStage);
        });

        HBox buttonBox = new HBox(10, addBookButton, backButton); // Pridanie Add Book pred tlačidlo Back
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
        buttonBox.setPadding(new Insets(10, 10, 20, 10));

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_LEFT);
        layout.getChildren().addAll(titleLabel, bookTable, buttonBox);

        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Available Books");
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

