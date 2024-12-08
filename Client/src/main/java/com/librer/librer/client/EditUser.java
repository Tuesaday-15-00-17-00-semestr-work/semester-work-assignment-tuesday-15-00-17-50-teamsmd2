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
import javafx.scene.control.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class EditUser {

    private final String apiUrl = "http://localhost:8080/api/users";  // Replace with your API URL
    private Stage editUserStage; // make this an instance variable

    public void start(Stage parentStage, UsersFetcher.User userToEdit, String adminName) {
        editUserStage = new Stage(); // assign the stage here
        editUserStage.setTitle("Edit User");

        // Labels and text fields for user details
        Label userIdLabel = new Label("User ID:");
        TextField userIdField = new TextField(userToEdit.getUserId() + "");
        userIdField.setEditable(false);  // Disable editing for userId

        Label userNameLabel = new Label("Name:");
        TextField userNameField = new TextField(userToEdit.getUserName());

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField(userToEdit.getEmail());

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();  // Use PasswordField instead of TextField
        passwordField.setText(userToEdit.getPassword());  // Set current password

        // Role selection
        Label roleLabel = new Label("Role:");
        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("Admin", "User");
        roleComboBox.setValue(userToEdit.getRoleId() == 1 ? "Admin" : "User");

        // Buttons
        Button changeButton = new Button("Change");
        changeButton.setOnAction(e -> {
            int userId = userToEdit.getUserId();  // Get the userId as integer directly from the User object
            String userName = userNameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText().isEmpty() ? userToEdit.getPassword() : passwordField.getText(); // Keep original password if empty
            String role = roleComboBox.getValue();

            if (updateUserDetails(userId, userName, email, password, role)) {  // Pass userId as integer
                editUserStage.close();
            }
            // Always reopen the ManageAccounts window
            ManageAccounts manageAccounts = new ManageAccounts();
            manageAccounts.start(parentStage, adminName); // Pass adminName to refresh context
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> editUserStage.close());

        // Layout setup
        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                userIdLabel, userIdField,
                userNameLabel, userNameField,
                emailLabel, emailField,
                passwordLabel, passwordField,
                roleLabel, roleComboBox,
                changeButton
        );
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20px;");

        // Place Cancel button at the bottom-right
        HBox cancelBox = new HBox(cancelButton);
        cancelBox.setAlignment(Pos.BOTTOM_RIGHT);
        cancelBox.setPadding(new Insets(10));

        layout.getChildren().add(cancelBox);

        Scene scene = new Scene(layout, 300, 400);
        editUserStage.setScene(scene);
        editUserStage.show();
    }

    // Method to send the updated user data to the backend
    private boolean updateUserDetails(int userId, String userName, String email, String password, String role) {
        try {
            // Determine roleId as integer (1 for Admin, 0 for User)
            int roleId = role.equals("Admin") ? 1 : 0;

            // Construct the JSON body for the update request with the correct key order
            String jsonBody = String.format("{\"user_id\": %d, \"username\": \"%s\", \"password\": \"%s\", \"role_id\": %d, \"email\": \"%s\"}",
                    userId, userName, password, roleId, email);

            // Print JSON being sent to the server
            System.out.println("Sending JSON to server: " + jsonBody);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl + "/" + userId)) // Assuming the API uses userId in the URL
                    .header("Content-Type", "application/json")
                    .method("PUT", HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            // Send the request using HttpClient
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("User updated successfully!");
                return true;
            } else {
                System.out.println("Failed to update user. Status code: " + response.statusCode());
                editUserStage.close(); // Close the EditUser stage on failure
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}









