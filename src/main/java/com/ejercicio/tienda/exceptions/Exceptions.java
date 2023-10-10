package com.ejercicio.tienda.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Exceptions extends RuntimeException {
    private String message;
    private HttpStatus httpStatus;

    public Exceptions(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
