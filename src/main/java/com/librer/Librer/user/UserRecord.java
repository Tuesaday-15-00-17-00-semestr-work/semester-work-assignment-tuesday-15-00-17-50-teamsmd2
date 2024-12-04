package com.librer.Librer.user;

import jakarta.validation.constraints.*;

public record UserRecord(
        @PositiveOrZero
        int user_id,
        String username,
        @NotEmpty
        String password,
        @PositiveOrZero
        int role_id,
        @Email
        @NotBlank
        String email
) {

    public UserRecord {

    }

}

