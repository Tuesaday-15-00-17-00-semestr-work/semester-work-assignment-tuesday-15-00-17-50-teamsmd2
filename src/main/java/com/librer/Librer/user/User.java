package com.librer.Librer.user;

import jakarta.validation.constraints.*;

public record User(
        @PositiveOrZero
        int user_id,
        @NotEmpty
        String username,
        @NotEmpty
        String password,
        @PositiveOrZero
        int role_id,
        @Email
        @NotBlank
        String email
) {

    public User {

    }

}

