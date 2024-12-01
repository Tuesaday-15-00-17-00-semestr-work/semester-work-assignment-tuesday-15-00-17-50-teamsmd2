package com.librer.librer.client;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class MyBooks {
    public void start(Stage primaryStage, String userName) {
        Label titleLabel = new Label("My Books");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        TableView<Book> bookTable = new TableView<>();
        bookTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Book, String> bookIdColumn = new TableColumn<>("Book ID");
        bookIdColumn.setCellValueFactory(cellData -> cellData.getValue().bookIdProperty());

        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());

        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());

        bookIdColumn.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.2));
        titleColumn.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.5));
        authorColumn.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.3));

        bookTable.getColumns().addAll(bookIdColumn, titleColumn, authorColumn);

        ObservableList<Book> books = FXCollections.observableArrayList(
                new Book("1", "Book Title 1", "Author 1"),
                new Book("2", "Book Title 2", "Author 2")
        );

        bookTable.setItems(books);

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 14px;");
        backButton.setOnAction(e -> {
            UserPage userPage = new UserPage(userName, primaryStage);
        });

        // Add Return Book button
        Button returnButton = new Button("Return Book");
        returnButton.setStyle("-fx-font-size: 14px; -fx-pref-width: 100px; -fx-pref-height: 30px;");
        returnButton.setOnAction(e -> showReturnBookWindow());

        // Swapped positions of the buttons
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

    // Method to show the Return Book window
    private void showReturnBookWindow() {
        Stage returnStage = new Stage();
        returnStage.setTitle("Return Book");

        Label bookIdLabel = new Label("Book ID:");
        TextField bookIdField = new TextField();

        Button returnBookButton = new Button("Return");
        returnBookButton.setOnAction(e -> {
            String bookId = bookIdField.getText();
            System.out.println("Returning Book: Book ID = " + bookId);
            returnStage.close();
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> returnStage.close());

        // Return button centered below the input field
        VBox buttonLayout = new VBox(10, returnBookButton, cancelButton);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.setPadding(new Insets(10));

        // Align the Cancel button at the bottom-right
        HBox cancelBox = new HBox(cancelButton);
        cancelBox.setAlignment(Pos.BOTTOM_RIGHT);
        cancelBox.setPadding(new Insets(10));

        VBox returnLayout = new VBox(10, bookIdLabel, bookIdField, buttonLayout, cancelBox);
        returnLayout.setAlignment(Pos.CENTER);
        returnLayout.setPadding(new Insets(20));

        Scene returnScene = new Scene(returnLayout, 250, 200);
        returnStage.setScene(returnScene);
        returnStage.show();
    }

    public static class Book {
        private final SimpleStringProperty bookId;
        private final SimpleStringProperty title;
        private final SimpleStringProperty author;

        public Book(String bookId, String title, String author) {
            this.bookId = new SimpleStringProperty(bookId);
            this.title = new SimpleStringProperty(title);
            this.author = new SimpleStringProperty(author);
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
    }
}



