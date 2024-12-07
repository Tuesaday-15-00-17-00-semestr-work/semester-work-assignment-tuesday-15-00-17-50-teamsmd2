package com.librer.librer.client;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import java.util.HashSet;
import java.util.Set;


public class MyBooks {
    private final BooksFetcher booksFetcher = new BooksFetcher();
    private final TransactionsFetcher transactionsFetcher = new TransactionsFetcher();
    private int userId;
    private Set<Integer> displayedBookIds = new HashSet<>();
    private TableView<BooksFetcher.Book> bookTable; // Make bookTable a class-level variable

    public MyBooks(int userId) {
        this.userId = userId;
    }

    public void start(Stage primaryStage, String userName) {
        Label titleLabel = new Label("My Books");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        bookTable = new TableView<>(); // Initialize bookTable here
        bookTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<BooksFetcher.Book, String> bookIdColumn = new TableColumn<>("Book ID");
        bookIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBookId()));

        TableColumn<BooksFetcher.Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));

        TableColumn<BooksFetcher.Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));

        bookIdColumn.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.2));
        titleColumn.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.5));
        authorColumn.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.3));

        bookTable.getColumns().addAll(bookIdColumn, titleColumn, authorColumn);

        // Fetch books data
        ObservableList<BooksFetcher.Book> books = booksFetcher.fetchData();
        System.out.println("Books loaded: " + books.size());

        // Fetch transactions and filter by userId
        ObservableList<TransactionsFetcher.Transaction> transactions = transactionsFetcher.fetchData();
        ObservableList<TransactionsFetcher.Transaction> userTransactions = transactionsFetcher.getUserTransactions(userId);

        ObservableList<BooksFetcher.Book> userBooks = FXCollections.observableArrayList();

        // Track borrowed books to check if they were later returned
        Set<Integer> returnedBooks = new HashSet<>();

        // Mark books as returned
        for (TransactionsFetcher.Transaction transaction : userTransactions) {
            if ("Returned".equals(transaction.getAction())) {
                returnedBooks.add(transaction.getBookId());
            }
        }

        // Add borrowed but not returned books
        for (TransactionsFetcher.Transaction transaction : userTransactions) {
            if ("Borrowed".equals(transaction.getAction()) && !returnedBooks.contains(transaction.getBookId())) {
                for (BooksFetcher.Book book : books) {
                    if (Integer.parseInt(book.getBookId()) == transaction.getBookId()) {
                        userBooks.add(book);
                        displayedBookIds.add(transaction.getBookId());  // Store ID of displayed book
                        break;
                    }
                }
            }
        }

        // Set the filtered books to the table
        bookTable.setItems(userBooks);

        // Add Return Book button
        Button returnButton = new Button("Return Book");
        returnButton.setStyle("-fx-font-size: 14px; -fx-pref-width: 100px; -fx-pref-height: 30px;");
        returnButton.setOnAction(e -> {
            // Zavoláme ReturnBook, kde odovzdáme parentStage, myBooks a userId
            ReturnBook returnBook = new ReturnBook();
            returnBook.start(primaryStage, this, userId); // Opravené predávanie správnych parametrov
        });

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 14px;");
        backButton.setOnAction(e -> {
            UserPage userPage = new UserPage(userName, primaryStage, userId);
        });

        HBox buttonBox = new HBox(10, returnButton, backButton);
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
        buttonBox.setPadding(new Insets(10, 10, 20, 10));

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_LEFT);
        layout.getChildren().addAll(titleLabel, bookTable, buttonBox);

        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("My Books");
        primaryStage.show();
    }

    // Getter for displayedBookIds to use in ReturnBook
    public Set<Integer> getDisplayedBookIds() {
        return displayedBookIds;
    }

    // Method to remove a returned book from displayedBookIds
    public void removeReturnedBook(int bookId) {
        displayedBookIds.remove(bookId);
        refreshBookList(); // Refresh the view after removing the book
    }

    // Method to refresh the book list after a book is returned
    public void refreshBookList() {
        ObservableList<BooksFetcher.Book> books = booksFetcher.fetchData(); // Fetch updated books
        ObservableList<BooksFetcher.Book> userBooks = FXCollections.observableArrayList();

        // Fetch transactions and filter by userId
        ObservableList<TransactionsFetcher.Transaction> transactions = transactionsFetcher.fetchData();
        ObservableList<TransactionsFetcher.Transaction> userTransactions = transactionsFetcher.getUserTransactions(userId);

        Set<Integer> returnedBooks = new HashSet<>();

        // Mark books as returned
        for (TransactionsFetcher.Transaction transaction : userTransactions) {
            if ("Returned".equals(transaction.getAction())) {
                returnedBooks.add(transaction.getBookId());
            }
        }

        // Add borrowed but not returned books
        for (TransactionsFetcher.Transaction transaction : userTransactions) {
            if ("Borrowed".equals(transaction.getAction()) && !returnedBooks.contains(transaction.getBookId())) {
                for (BooksFetcher.Book book : books) {
                    if (Integer.parseInt(book.getBookId()) == transaction.getBookId()) {
                        userBooks.add(book);
                        displayedBookIds.add(transaction.getBookId());
                        break;
                    }
                }
            }
        }

        // Set the updated list to the table
        bookTable.setItems(userBooks);
    }
}




