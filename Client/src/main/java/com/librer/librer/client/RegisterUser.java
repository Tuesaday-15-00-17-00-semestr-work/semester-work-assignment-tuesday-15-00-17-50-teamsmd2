package com.librer.librer.client;

import javafx.application.Application;
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

public class RegisterUser {
    private final HttpClient client = HttpClient.newHttpClient();
    private final ManageAccounts manageAccounts;

    public RegisterUser(ManageAccounts manageAccounts) {
        this.manageAccounts = manageAccounts; // Passing reference to ManageAccounts
    }

    public void start(Stage stage) {
        // Výstupná plocha
        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);

        // Nadpis a formulár
        Label titleLabel = new Label("Add User");
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

        // Tlačidlo pre pridanie používateľa
        Button addButton = new Button("Add User");
        addButton.setOnAction(e -> {
            boolean isValid = true;
            String username = nameField.getText();
            String password = passwordField.getText();
            int roleId = 0; // Predvolená rola
            String email = emailField.getText();

            // Resetovanie farieb polí
            emailField.setStyle("-fx-border-color: none;");
            nameField.setStyle("-fx-border-color: none;");
            passwordField.setStyle("-fx-border-color: none;");

            // Validačné pravidlá
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

            if (isValid) {
                String requestBody = String.format("{\"username\": \"%s\", \"password\": \"%s\", \"roleId\": %d, \"email\": \"%s\"}",
                        username, password, roleId, email);
                String response = makeRequest("http://localhost:8080/api/users", "POST", requestBody);
                outputArea.setText(response);
                System.out.println("Používateľ bol pridaný: " + username + " s emailom: " + email);

                // Update the table after user is added
                manageAccounts.refreshTable();

                stage.close();
            } else {
                outputArea.setText("Vyplňte všetky požadované polia.");
            }
        });

        // Tlačidlo pre zrušenie
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> stage.close());

        // Rozloženie prvkov
        VBox inputBox = new VBox(10, emailLabel, emailField, nameLabel, nameField, passwordLabel, passwordField, addButton);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setStyle("-fx-padding: 20px;");

        HBox buttonBox = new HBox(10, cancelButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setPadding(new Insets(10));

        // Hlavné rozloženie
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        root.setCenter(inputBox);
        root.setBottom(buttonBox);
        BorderPane.setAlignment(buttonBox, Pos.BOTTOM_RIGHT);

        // Nastavenie scény
        Scene scene = new Scene(root, 300, 350);
        stage.setScene(scene);
        stage.setTitle("Add User");
        stage.show();
    }

    private String makeRequest(String url, String method, String body) {
        try {
            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(URI.create(url));

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
            return "Chyba: " + e.getMessage();
        }
    }
}




