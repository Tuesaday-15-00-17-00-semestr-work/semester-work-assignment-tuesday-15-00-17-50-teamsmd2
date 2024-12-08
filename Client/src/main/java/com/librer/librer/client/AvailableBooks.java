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
import javafx.collections.ObservableList;


public class AvailableBooks {
    private int userId;

    // Modify constructor to accept userId
    public AvailableBooks(int userId) {
        this.userId = userId;
    }

    public void start(Stage primaryStage, String userName, int userId) {
        Label titleLabel = new Label("Available Books");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        TableView<BooksFetcher.Book> bookTable = new TableView<>();
        bookTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<BooksFetcher.Book, String> bookIdColumn = new TableColumn<>("Book ID");
        bookIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBookId()));

        TableColumn<BooksFetcher.Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));

        TableColumn<BooksFetcher.Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));

        TableColumn<BooksFetcher.Book, String> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsbn()));

        TableColumn<BooksFetcher.Book, String> availableCopiesColumn = new TableColumn<>("Available Copies");
        availableCopiesColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAvailableCopies()));

        bookIdColumn.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.1));
        titleColumn.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.25));
        authorColumn.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.25));
        isbnColumn.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.2));
        availableCopiesColumn.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.2));

        bookTable.getColumns().addAll(bookIdColumn, titleColumn, authorColumn, isbnColumn, availableCopiesColumn);

        BooksFetcher fetcher = new BooksFetcher();
        ObservableList<BooksFetcher.Book> books = fetcher.fetchData();

        // Filter books with available copies greater than 0
        ObservableList<BooksFetcher.Book> availableBooks = books.filtered(book ->
                Integer.parseInt(book.getAvailableCopies()) > 0
        );
        bookTable.setItems(availableBooks);

        // Print the book IDs and user ID to the console
        System.out.println("User ID: " + userId);  // Print user ID
        availableBooks.forEach(book -> {
            System.out.println("Book ID: " + book.getBookId());
        });

        Button borrowBookButton = new Button("Borrow Book");
        borrowBookButton.setStyle("-fx-font-size: 14px;");
        borrowBookButton.setOnAction(e -> {
            BorrowBook borrowBook = new BorrowBook(userId); // Pass userId to BorrowBook
            borrowBook.start(primaryStage, availableBooks, this); // Pass availableBooks to BorrowBook
        });

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 14px;");
        backButton.setOnAction(e -> {
            UserPage userPage = new UserPage(userName, primaryStage, userId);
        });

        HBox buttonBox = new HBox(10, borrowBookButton, backButton);
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

    // Method to refresh available books
    public void refreshAvailableBooks(Stage primaryStage, String userName, int userId) {
        start(primaryStage, userName, userId); // Call start again to reload data
    }
}





