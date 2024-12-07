package com.librer.librer.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;


public class ManageBooks {
    private final BooksFetcher booksFetcher;
    private TableView<BooksFetcher.Book> bookTable;

    // Constructor to initialize booksFetcher
    public ManageBooks(BooksFetcher booksFetcher) {
        this.booksFetcher = booksFetcher;
    }

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
        bookTable = new TableView<>();
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
        titleColumn.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.3));
        authorColumn.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.25));
        isbnColumn.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.2));
        availableCopiesColumn.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.15));

        bookTable.getColumns().addAll(bookIdColumn, titleColumn, authorColumn, isbnColumn, availableCopiesColumn);

        // Fetch books using BooksFetcher and populate the table
        ObservableList<BooksFetcher.Book> books = booksFetcher.fetchData();
        bookTable.setItems(books);

        Button addBookButton = new Button("Add Book");
        addBookButton.setStyle("-fx-font-size: 14px;");
        addBookButton.setOnAction(e -> {
            AddBook addBook = new AddBook();
            Stage addBookStage = new Stage();
            addBook.start(addBookStage, this); // Pass 'this' to refresh the list
        });

        Button updateBookButton = new Button("Update Book");
        updateBookButton.setStyle("-fx-font-size: 14px;");
        updateBookButton.setOnAction(e -> {
            // Get the selected book from the table
            BooksFetcher.Book selectedBook = bookTable.getSelectionModel().getSelectedItem();

            // Check if a book is selected
            if (selectedBook != null) {
                // Open the Update Book window with the selected book passed
                UpdateBook updateBook = new UpdateBook(this); // Pass 'this' to manage refresh
                Stage updateBookStage = new Stage();
                updateBook.start(updateBookStage, selectedBook); // Pass the selected book to the UpdateBook window
            } else {
                // Show alert if no book is selected
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a book to update.", ButtonType.OK);
                alert.showAndWait();
            }
        });

        Button removeBookButton = new Button("Remove Book");
        removeBookButton.setStyle("-fx-font-size: 14px;");
        removeBookButton.setOnAction(e -> {
            RemoveBook removeBook = new RemoveBook(this);
            removeBook.start(primaryStage); // Opens the remove book window
        });

        HBox buttonBox = new HBox(10, addBookButton, updateBookButton, removeBookButton, backButton);
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

    public void refreshBookList() {
        // Clear the current list of books from the table
        bookTable.getItems().clear();

        // Fetch updated books and populate the table again
        ObservableList<BooksFetcher.Book> books = booksFetcher.fetchData();
        bookTable.setItems(books); // Set the new list of books to the table
    }
}


