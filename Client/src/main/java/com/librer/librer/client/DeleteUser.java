package com.librer.librer.client;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class DeleteUser {
    private final HttpClient client = HttpClient.newHttpClient();
    private final ManageAccounts manageAccounts;

    public DeleteUser(ManageAccounts manageAccounts) {
        this.manageAccounts = manageAccounts; // Passing reference to ManageAccounts
    }

    public void start(Stage parentStage) {
        Stage deleteUserStage = new Stage();
        deleteUserStage.setTitle("Delete User");

        // Label and text field for User ID
        Label userIdLabel = new Label("User ID:");
        TextField userIdField = new TextField();

        // Buttons
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            String userId = userIdField.getText();

            if (!userId.isEmpty()) {
                String response = deleteUserById(userId);
                System.out.println(response);  // Output the response

                // Refresh the table after deletion
                manageAccounts.refreshTable();

                deleteUserStage.close();  // Close the window after deleting
            } else {
                userIdField.setStyle("-fx-border-color: red;");
                System.out.println("Please enter a User ID.");
            }
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> deleteUserStage.close());

        // Layout setup
        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                userIdLabel, userIdField,
                deleteButton
        );
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20px;");

        // Place Cancel button at the bottom-right
        HBox cancelBox = new HBox(cancelButton);
        cancelBox.setAlignment(Pos.BOTTOM_RIGHT);
        cancelBox.setPadding(new Insets(10));

        layout.getChildren().add(cancelBox);

        Scene scene = new Scene(layout, 300, 150);
        deleteUserStage.setScene(scene);
        deleteUserStage.show();
    }

    private String deleteUserById(String userId) {
        try {
            String url = "http://localhost:8080/api/users/" + userId;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .DELETE()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return "User deleted successfully.";
            } else {
                return "Error: " + response.body();
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}


