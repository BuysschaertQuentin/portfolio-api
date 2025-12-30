package com.portfolio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when user lacks permission to access a resource.
 * Maps to HTTP 403 Forbidden.
 * 
 * @see PortfolioException
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends PortfolioException {

    public ForbiddenException(String message) {
        super(message, "FORBIDDEN");
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, "FORBIDDEN", cause);
    }
}
