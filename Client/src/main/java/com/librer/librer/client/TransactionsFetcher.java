package com.librer.librer.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TransactionsFetcher {
    private final HttpClient client = HttpClient.newHttpClient();
    private ObservableList<Transaction> transactions = FXCollections.observableArrayList();

    public ObservableList<Transaction> fetchData() {
        String response = makeRequest("http://localhost:8080/api/transactions", "GET", null);
        parseTransactionsResponse(response);
        return transactions;
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

    private void parseTransactionsResponse(String response) {
        try {
            response = response.substring(1, response.length() - 1); // Odstrániť vonkajšie hranaté zátvorky
            String[] transactionEntries = response.split("},\\{");

            for (String entry : transactionEntries) {
                entry = entry.replaceAll("[{}]", ""); // Odstrániť zložené zátvorky

                String[] fields = entry.split(",");
                int transactionId = Integer.parseInt(extractValue(fields[0])); // transaction_id ako int
                int userId = Integer.parseInt(extractValue(fields[1])); // user_id ako int
                int bookId = Integer.parseInt(extractValue(fields[2])); // book_id ako int
                String action = extractValue(fields[3]); // action ako String
                String date = extractValue(fields[4]); // date ako String

                transactions.add(new Transaction(transactionId, userId, bookId, action, date));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String extractValue(String field) {
        return field.split(":")[1].replace("\"", "").trim();
    }

    public ObservableList<Transaction> getUserTransactions(int userId) {
        return transactions.filtered(transaction -> transaction.getUserId() == userId);
    }

    public static class Transaction {
        private final int transactionId;
        private final int userId;
        private final int bookId;
        private final String action;
        private final String date;

        public Transaction(int transactionId, int userId, int bookId, String action, String date) {
            this.transactionId = transactionId;
            this.userId = userId;
            this.bookId = bookId;
            this.action = action;
            this.date = date;
        }

        public int getTransactionId() {
            return transactionId;
        }

        public int getUserId() {
            return userId;
        }

        public int getBookId() {
            return bookId;
        }

        public String getAction() {
            return action;
        }

        public String getDate() {
            return date;
        }
    }
}

