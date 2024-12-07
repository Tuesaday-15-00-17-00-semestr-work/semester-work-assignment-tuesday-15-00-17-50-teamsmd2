package com.librer.librer.client;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


public class Registration {
    private final UsersFetcher usersFetcher = new UsersFetcher();
    private final HttpClient client = HttpClient.newHttpClient();

    public void start(Stage stage) {
        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);

        Label titleLabel = new Label("Registration");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        emailField.setPromptText("Enter email");

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter name");

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");

        Label confirmPasswordLabel = new Label("Confirm Password:");
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Re-enter password");

        Button addButton = new Button("Add User");
        addButton.setOnAction(e -> {
            boolean isValid = true;
            String username = nameField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            String email = emailField.getText();

            emailField.setStyle("-fx-border-color: none;");
            nameField.setStyle("-fx-border-color: none;");
            passwordField.setStyle("-fx-border-color: none;");
            confirmPasswordField.setStyle("-fx-border-color: none;");

            if (email.isEmpty() || !email.contains("@") || email.indexOf('@') == 0 || email.indexOf('@') == email.length() - 1) {
                emailField.setStyle("-fx-border-color: red;");
                isValid = false;
            }
            if (username.isEmpty()) {
                nameField.setStyle("-fx-border-color: red;");
                isValid = false;
            }
            if (password.isEmpty()) {
                passwordField.setStyle("-fx-border-color: red;");
                isValid = false;
            }
            if (!password.equals(confirmPassword)) {
                confirmPasswordField.setStyle("-fx-border-color: red;");
                isValid = false;
            }

            if (isValid) {
                List<UsersFetcher.User> users = usersFetcher.fetchData();

                if (users.stream().anyMatch(user -> user.getEmail().equalsIgnoreCase(email))) {
                    emailField.setStyle("-fx-border-color: red;");
                    outputArea.setText("Email already exists.");
                    isValid = false;
                }
                if (users.stream().anyMatch(user -> user.getUserName().equalsIgnoreCase(username))) {
                    nameField.setStyle("-fx-border-color: red;");
                    outputArea.setText("Username already exists.");
                    isValid = false;
                }
            }

            if (isValid) {
                String requestBody = String.format("{\"username\": \"%s\", \"password\": \"%s\", \"roleId\": 0, \"email\": \"%s\"}",
                        username, password, email);
                String response = makeRequest("http://localhost:8080/api/users", "POST", requestBody);
                outputArea.setText("User has been successfully added.");
                stage.close();
            } else {
                if (outputArea.getText().isEmpty()) {
                    outputArea.setText("Please fill in all required fields correctly.");
                }
            }
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> stage.close());

        VBox inputBox = new VBox(10, emailLabel, emailField, nameLabel, nameField, passwordLabel, passwordField, confirmPasswordLabel, confirmPasswordField, addButton);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setStyle("-fx-padding: 20px;");

        HBox buttonBox = new HBox(10, cancelButton);
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
        buttonBox.setPadding(new Insets(5, 10, 10, 10));

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        root.setCenter(inputBox);
        root.setBottom(buttonBox);

        Scene scene = new Scene(root, 300, 420);
        stage.setScene(scene);
        stage.setTitle("Registration");
        stage.show();
    }

    private String makeRequest(String url, String method, String body) {
        try {
            HttpRequest.Builder builder = HttpRequest.newBuilder().uri(URI.create(url));

            if ("POST".equalsIgnoreCase(method) && body != null) {
                builder.POST(HttpRequest.BodyPublishers.ofString(body))
                        .header("Content-Type", "application/json");
            } else {
                builder.GET();
            }

            HttpRequest request = builder.build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error when sending the request: " + e.getMessage();
        }
    }
}
