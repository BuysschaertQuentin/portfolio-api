package com.portfolio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a resource already exists (duplicate).
 * Maps to HTTP 409 Conflict.
 * 
 * @see PortfolioException
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends PortfolioException {

    public ConflictException(String message) {
        super(message, "CONFLICT");
    }

    /**
     * Creates a ConflictException with a formatted message.
     * 
     * @param resourceName the name of the resource (e.g., "User", "Skill")
     * @param fieldName    the field that caused the conflict (e.g., "email", "slug")
     * @param fieldValue   the duplicate value
     */
    public ConflictException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s already exists with %s: '%s'", resourceName, fieldName, fieldValue), "CONFLICT");
    }

    public ConflictException(String message, Throwable cause) {
        super(message, "CONFLICT", cause);
    }
}
