package com.portfolio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when request is malformed or contains invalid data.
 * Maps to HTTP 400 Bad Request.
 * 
 * @see PortfolioException
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends PortfolioException {

    public BadRequestException(String message) {
        super(message, "BAD_REQUEST");
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, "BAD_REQUEST", cause);
    }
}
