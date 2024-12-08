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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class MyBooks {
    private final BooksFetcher booksFetcher = new BooksFetcher();
    private final TransactionsFetcher transactionsFetcher = new TransactionsFetcher();
    private int userId;
    private Set<Integer> displayedBookIds = new HashSet<>();
    private TableView<BooksFetcher.Book> bookTable;

    public MyBooks(int userId) {
        this.userId = userId;
    }

    public void start(Stage primaryStage, String userName) {
        Label titleLabel = new Label("My Books");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        bookTable = new TableView<>();
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

        ObservableList<BooksFetcher.Book> books = booksFetcher.fetchData();
        System.out.println("Books loaded: " + books.size());

        ObservableList<TransactionsFetcher.Transaction> transactions = transactionsFetcher.fetchData();
        ObservableList<TransactionsFetcher.Transaction> userTransactions = transactionsFetcher.getUserTransactions(userId);

        ObservableList<BooksFetcher.Book> userBooks = FXCollections.observableArrayList();

        // Map to track borrow and return counts for each book
        Map<Integer, Integer> borrowCount = new HashMap<>();
        Map<Integer, Integer> returnCount = new HashMap<>();

        for (TransactionsFetcher.Transaction transaction : userTransactions) {
            int bookId = transaction.getBookId();
            if ("Borrowed".equals(transaction.getAction())) {
                borrowCount.put(bookId, borrowCount.getOrDefault(bookId, 0) + 1);
            } else if ("Returned".equals(transaction.getAction())) {
                returnCount.put(bookId, returnCount.getOrDefault(bookId, 0) + 1);
            }
        }

        // Add books to the list based on borrow - return count difference
        for (BooksFetcher.Book book : books) {
            int bookId = Integer.parseInt(book.getBookId());
            int borrowed = borrowCount.getOrDefault(bookId, 0);
            int returned = returnCount.getOrDefault(bookId, 0);

            int timesToAdd = borrowed - returned;
            if (timesToAdd > 0) {
                for (int i = 0; i < timesToAdd; i++) {
                    userBooks.add(book);
                    displayedBookIds.add(bookId);
                }
            }
        }

        bookTable.setItems(userBooks);

        Button returnButton = new Button("Return Book");
        returnButton.setStyle("-fx-font-size: 14px; -fx-pref-width: 100px; -fx-pref-height: 30px;");
        returnButton.setOnAction(e -> {
            ReturnBook returnBook = new ReturnBook();
            returnBook.start(primaryStage, this, userId);
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

    public Set<Integer> getDisplayedBookIds() {
        return displayedBookIds;
    }

    public void removeReturnedBook(int bookId) {
        displayedBookIds.remove(bookId);
        refreshBookList();
    }

    public void refreshBookList() {
        ObservableList<BooksFetcher.Book> books = booksFetcher.fetchData();
        ObservableList<BooksFetcher.Book> userBooks = FXCollections.observableArrayList();

        ObservableList<TransactionsFetcher.Transaction> transactions = transactionsFetcher.fetchData();
        ObservableList<TransactionsFetcher.Transaction> userTransactions = transactionsFetcher.getUserTransactions(userId);

        Map<Integer, Integer> borrowCount = new HashMap<>();
        Map<Integer, Integer> returnCount = new HashMap<>();

        for (TransactionsFetcher.Transaction transaction : userTransactions) {
            int bookId = transaction.getBookId();
            if ("Borrowed".equals(transaction.getAction())) {
                borrowCount.put(bookId, borrowCount.getOrDefault(bookId, 0) + 1);
            } else if ("Returned".equals(transaction.getAction())) {
                returnCount.put(bookId, returnCount.getOrDefault(bookId, 0) + 1);
            }
        }

        for (BooksFetcher.Book book : books) {
            int bookId = Integer.parseInt(book.getBookId());
            int borrowed = borrowCount.getOrDefault(bookId, 0);
            int returned = returnCount.getOrDefault(bookId, 0);

            int timesToAdd = borrowed - returned;
            if (timesToAdd > 0) {
                for (int i = 0; i < timesToAdd; i++) {
                    userBooks.add(book);
                    displayedBookIds.add(bookId);
                }
            }
        }

        bookTable.setItems(userBooks);
    }
}






