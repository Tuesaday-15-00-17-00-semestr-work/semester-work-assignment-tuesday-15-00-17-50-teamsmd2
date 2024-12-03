package com.librer.Librer.trans;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransController {

    private final TransRepository transactionRepository;

    public TransController(TransRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    // Get all transactions
    @GetMapping("")
    List<Trans> findAll() {
        return transactionRepository.findAll();
    }

    // Get a transaction by ID
    @GetMapping("/{transactionId}")
    Trans findById(@PathVariable int transactionId) {

        Optional<Trans> transaction = transactionRepository.findById(transactionId);
        if (transaction.isEmpty()) {
            throw new TransNotFoundException();
        }
        return transaction.get();
    }

    // Create a new transaction
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public void create(@Valid @RequestBody Trans transaction) {
        transactionRepository.create(transaction);
    }

    // Update an existing transaction
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{transactionId}")
    void update(@Valid @RequestBody Trans transaction, @PathVariable int transactionId) {
        transactionRepository.update(transaction, transactionId);
    }

    // Delete a transaction by ID
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{transactionId}")
    void delete(@PathVariable int transactionId) {
        transactionRepository.delete(transactionId);
    }

    // Search for transactions by user ID
    @GetMapping("/search/user/{userId}")
    List<Trans> findByUserId(@PathVariable int userId) {
        List<Trans> transactions = transactionRepository.findAllByUserId(userId);
        if (transactions.isEmpty()) {
            throw new TransNotFoundException();
        }
        return transactions;
    }

    // Search for transactions by book ID
    @GetMapping("/search/book/{bookId}")
    List<Trans> findByBookId(@PathVariable int bookId) {
        List<Trans> transactions = transactionRepository.findAllByBookId(bookId);
        if (transactions.isEmpty()) {
            throw new TransNotFoundException();
        }
        return transactions;
    }

    // Search for transactions by action (borrow/return)
    @GetMapping("/search/action/{action}")
    List<Trans> findByAction(@PathVariable String action) {
        List<Trans> transactions = transactionRepository.findAllByAction(action);
        if (transactions.isEmpty()) {
            throw new TransNotFoundException();
        }
        return transactions;
    }

}
