package com.librer.Librer.trans;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TransNotFoundException extends RuntimeException {
    public TransNotFoundException() {
        super("Transaction not found");
    }
}
