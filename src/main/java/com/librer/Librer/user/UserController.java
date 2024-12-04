package com.librer.Librer.user;

import com.librer.Librer.book.Book;
import com.librer.Librer.book.BookNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("")
    List<UserRecord> findAll() {
        return userRepository.findAll();
    }

    @GetMapping("/{userId}")
    UserRecord findById(@PathVariable int userId) {
        Optional<UserRecord> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return user.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@Valid @RequestBody UserRecord user) {
        userRepository.create(user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{userId}")
    void update(@Valid @RequestBody UserRecord user, @PathVariable int userId) {
        userRepository.update(user, userId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}")
    void delete(@PathVariable int userId) {
        userRepository.delete(userId);
    }

    @GetMapping("/search/{username}")
    List<UserRecord> search(@PathVariable String username) {
        List<UserRecord> users = userRepository.findAllByUsername(username);
        if (users.isEmpty()) {
            throw new UserNotFoundException();
        }
        return users;
    }

}
