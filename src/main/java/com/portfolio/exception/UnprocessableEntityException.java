package com.portfolio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when request is syntactically correct but semantically invalid.
 * Maps to HTTP 422 Unprocessable Entity.
 * 
 * @see PortfolioException
 */
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntityException extends PortfolioException {

    public UnprocessableEntityException(String message) {
        super(message, "UNPROCESSABLE_ENTITY");
    }

    public UnprocessableEntityException(String message, Throwable cause) {
        super(message, "UNPROCESSABLE_ENTITY", cause);
    }
}
