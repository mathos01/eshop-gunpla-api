package fr.mechatmos.gunpla_eshop_api.exceptions;

public class JwtValidationException extends RuntimeException {
    public JwtValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
