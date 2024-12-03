package com.librer.Librer.trans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;



@Repository
public class TransRepository {

    private static final Logger log = LoggerFactory.getLogger(Trans.class);
    private final JdbcClient jdbcClient;

    public TransRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    // Find all transactions
    public List<Trans> findAll() {
        return jdbcClient.sql("SELECT * FROM Transactions")
                .query(Trans.class)
                .list();
    }

    // Find a transaction by ID
    public Optional<Trans> findById(int transactionId) {
        return jdbcClient.sql("SELECT * FROM Transactions WHERE transaction_id = ?")
                .params(List.of(transactionId))
                .query(Trans.class)
                .optional();
    }

    // Create a new transaction
    public void create(Trans transaction) {
        var updated = jdbcClient.sql("INSERT INTO Transactions (user_id, book_id, action, date) VALUES (?, ?, ?, ?)")
                .params(List.of(transaction.userId(), transaction.bookId(), transaction.action(), transaction.date()))
                .update();
        Assert.state(updated == 1, "Failed to insert Transaction for User ID: " + transaction.userId());
    }

    // Update an existing transaction
    public void update(Trans transaction, int transactionId) {
        var updated = jdbcClient.sql("UPDATE Transactions SET user_id = ?, book_id = ?, action = ?, date = ? WHERE transaction_id = ?")
                .params(List.of(transaction.userId(), transaction.bookId(), transaction.action(), transaction.date(), transactionId))
                .update();
        Assert.state(updated == 1, "Failed to update Transaction with ID: " + transactionId);
    }

    // Delete a transaction by ID
    public void delete(int transactionId) {
        var updated = jdbcClient.sql("DELETE FROM Transactions WHERE transaction_id = ?")
                .params(List.of(transactionId))
                .update();
        Assert.state(updated == 1, "Failed to delete Transaction with ID: " + transactionId);
    }

    // Count total number of transactions
    public int count() {
        return jdbcClient.sql("SELECT * FROM Transactions")
                .query().listOfRows().size();
    }

    // Save all transactions
    public void saveAll(List<Trans> transactions) {
        transactions.forEach(this::create);
    }

    // Find all transactions by user ID
    public List<Trans> findAllByUserId(int userId) {
        return jdbcClient.sql("SELECT * FROM Transactions WHERE user_id = ?")
                .params(List.of(userId))
                .query(Trans.class)
                .list();
    }

    // Find all transactions by book ID
    public List<Trans> findAllByBookId(int bookId) {
        return jdbcClient.sql("SELECT * FROM Transactions WHERE book_id = ?")
                .params(List.of(bookId))
                .query(Trans.class)
                .list();
    }

    // Find all transactions by action (borrow/return)
    public List<Trans> findAllByAction(String action) {
        return jdbcClient.sql("SELECT * FROM Transactions WHERE action LIKE ?")
                .params(List.of("%" + action + "%"))
                .query(Trans.class)
                .list();
    }
}
