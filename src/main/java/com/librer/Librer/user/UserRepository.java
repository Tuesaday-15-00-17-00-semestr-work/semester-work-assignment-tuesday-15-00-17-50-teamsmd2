package com.librer.Librer.user;

import com.librer.Librer.book.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);
    private final JdbcClient jdbcClient;

    public UserRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<UserRecord> findAll() {
        return jdbcClient.sql("SELECT * FROM users;")
                .query(UserRecord.class)
                .list();
    }

    public Optional<UserRecord> findById(int userId) {
        return jdbcClient.sql("SELECT user_id, username, password, role_id, email FROM users WHERE user_id = ?")
                .params(List.of(userId))
                .query(UserRecord.class)
                .optional();
    }

    public void create(UserRecord user) {
        var updated = jdbcClient.sql("INSERT INTO users (username, password, role_id, email) VALUES (?, ?, ?, ?)")
                .params(List.of(user.username(), user.password(), user.role_id(), user.email()))
                .update();
        Assert.state(updated == 1, "Failed to insert User" + user.username());
    }

    public void update(UserRecord user, int userId) {
        var updated = jdbcClient.sql("UPDATE users SET username = ?, password = ?, role_id = ?, email = ? WHERE user_id = ?")
                .params(List.of(user.username(), user.password(), user.role_id(), user.email(), userId))
                .update();
        Assert.state(updated == 1, "Failed to update User" + user.username());
    }

    public void delete(int userId) {
        var updated = jdbcClient.sql("DELETE FROM users WHERE user_id = ?")
                .params(List.of(userId))
                .update();
        Assert.state(updated == 1, "Failed to delete User with id" + userId);
    }

    public int count() {
        return jdbcClient.sql("SELECT * FROM users")
                .query().listOfRows().size();
    }

    public  List<UserRecord> findAllByUsername(String username) {
        return jdbcClient.sql("SELECT * FROM users WHERE username LIKE ?")
                .params(List.of("%" + username + "%"))
                .query(UserRecord.class)
                .list();
    }

    public UserRecord findByEmail(String email) {
        return jdbcClient.sql("SELECT * FROM users WHERE email LIKE ?")
                .params(List.of(email))
                .query(UserRecord.class)
                .optional()
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

}
