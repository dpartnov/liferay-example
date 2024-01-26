package eu.lundegaard.calculator.rest.exception;

/**
 * Custom exception, may be used when user's data not valid.
 * @author Denys Partnov, denys.partnov@lundegaard.eu, 2022
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
