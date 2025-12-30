package com.portfolio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when authentication fails.
 * Maps to HTTP 401 Unauthorized.
 * 
 * @see PortfolioException
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthenticationException extends PortfolioException {

    public AuthenticationException(String message) {
        super(message, "AUTHENTICATION_ERROR");
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, "AUTHENTICATION_ERROR", cause);
    }
}
