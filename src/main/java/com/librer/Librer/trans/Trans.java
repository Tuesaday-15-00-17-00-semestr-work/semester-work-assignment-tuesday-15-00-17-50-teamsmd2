package com.librer.Librer.trans;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;
import java.sql.Date;
import java.time.LocalDate;

public record Trans(
        @PositiveOrZero
        int transactionId,

        @PositiveOrZero
        int userId,

        @PositiveOrZero
        int bookId,

        @NotEmpty
        String action,

        @NotNull
        String date
) {

    // Optional explicit constructor
    public Trans {
        // Add custom validation logic here if needed.
    }
}
