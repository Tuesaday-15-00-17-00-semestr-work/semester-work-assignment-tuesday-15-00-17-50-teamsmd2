package com.librer.Librer.book;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;

public record Book(
        @PositiveOrZero
        int id,
        @NotEmpty
        String title,
        @NotEmpty
        String author,
        @NotEmpty
        String isbn,
        @PositiveOrZero
        int availableCopies
) {

    public Book {

    }

}

