package com.portfolio.exception;

import lombok.Getter;

/**
 * Base exception class for all Portfolio application exceptions.
 * 
 * <p>This is the root exception that all domain-specific exceptions should extend.
 * It provides a standardized error code mechanism for consistent error handling
 * across the entire application.</p>
 * 
 * <h2>Exception Hierarchy:</h2>
 * <pre>
 * PortfolioException (base)
 * ├── AuthenticationException   → 401 Unauthorized
 * ├── ForbiddenException        → 403 Forbidden
 * ├── BadRequestException       → 400 Bad Request
 * ├── ResourceNotFoundException → 404 Not Found
 * ├── ConflictException         → 409 Conflict
 * └── UnprocessableEntityException → 422 Unprocessable Entity
 * </pre>
 * 
 * <h2>Usage Example:</h2>
 * <pre>{@code
 * public void deleteUser(UUID id) throws PortfolioException {
 *     User user = userRepository.findById(id)
 *         .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
 *     userRepository.delete(user);
 * }
 * }</pre>
 * 
 * @author Portfolio API
 * @since 1.0.0
 * @see AuthenticationException
 * @see ForbiddenException
 * @see BadRequestException
 * @see ResourceNotFoundException
 * @see ConflictException
 * @see UnprocessableEntityException
 */
@Getter
public class PortfolioException extends Exception {

    /**
     * Machine-readable error code for programmatic error handling.
     * Examples: "RESOURCE_NOT_FOUND", "AUTHENTICATION_ERROR", "CONFLICT"
     */
    private final String errorCode;

    /**
     * Creates a new PortfolioException with the specified message.
     * Uses default error code "PORTFOLIO_ERROR".
     *
     * @param message the detail message
     */
    public PortfolioException(String message) {
        super(message);
        this.errorCode = "PORTFOLIO_ERROR";
    }

    /**
     * Creates a new PortfolioException with the specified message and error code.
     *
     * @param message   the detail message
     * @param errorCode the machine-readable error code
     */
    public PortfolioException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Creates a new PortfolioException with the specified message and cause.
     * Uses default error code "PORTFOLIO_ERROR".
     *
     * @param message the detail message
     * @param cause   the cause of this exception
     */
    public PortfolioException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "PORTFOLIO_ERROR";
    }

    /**
     * Creates a new PortfolioException with the specified message, error code, and cause.
     *
     * @param message   the detail message
     * @param errorCode the machine-readable error code
     * @param cause     the cause of this exception
     */
    public PortfolioException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
