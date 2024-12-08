package com.librer.librer.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class UsersFetcher {
    private final HttpClient client = HttpClient.newHttpClient();
    private ObservableList<User> users = FXCollections.observableArrayList();

    public ObservableList<User> fetchData() {
        String response = makeRequest("http://localhost:8080/api/users", "GET", null);
        parseUsersResponse(response);
        return users;
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
            return "";
        }
    }

    private void parseUsersResponse(String response) {
        try {
            response = response.substring(1, response.length() - 1); // Remove outer brackets
            String[] userEntries = response.split("},\\{");

            for (String entry : userEntries) {
                entry = entry.replaceAll("[{}]", ""); // Remove curly braces

                String[] fields = entry.split(",");
                int userId = Integer.parseInt(extractValue(fields[0])); // Parse userId as int
                String userName = extractValue(fields[1]);
                String password = extractValue(fields[2]); // Assuming password is the 3rd field
                int roleId = Integer.parseInt(extractValue(fields[3])); // Parse roleId as int
                String email = extractValue(fields[4]);

                users.add(new User(userId, userName, password, roleId, email));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String extractValue(String field) {
        return field.split(":")[1].replace("\"", "").trim();
    }

    public static class User {
        private final int userId;
        private final String userName;
        private final String password;
        private final int roleId;
        private final String email;

        public User(int userId, String userName, String password, int roleId, String email) {
            this.userId = userId;
            this.userName = userName;
            this.password = password;
            this.roleId = roleId;
            this.email = email;
        }

        public int getUserId() {
            return userId;
        }

        public String getUserName() {
            return userName;
        }

        public String getPassword() {
            return password;
        }

        public int getRoleId() {
            return roleId;
        }

        public String getEmail() {
            return email;
        }
    }
}




