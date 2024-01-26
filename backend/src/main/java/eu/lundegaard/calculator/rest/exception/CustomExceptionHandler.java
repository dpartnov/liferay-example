package eu.lundegaard.calculator.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Exception handler implementation for mapping errors to
 * response entity.
 * @author Denys Partnov, denys.partnov@lundegaard.eu, 2022
 */
@ControllerAdvice
public class CustomExceptionHandler {

    /**
     * Handler for {@link MethodArgumentNotValidException} errors.
     * @param ex {@link MethodArgumentNotValidException} exception
     * @param request {@link WebRequest}
     * @return Response entity with list of {@link ExceptionDto}.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<List<ExceptionDto>> handleMethodArgumentNotValidException(Exception ex, WebRequest request) {
        MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getAllErrors().stream()
                        .map(e -> new ExceptionDto((e.getDefaultMessage())))
                        .collect(Collectors.toList()));
    }

    /**
     * Handler for {@link MethodArgumentTypeMismatchException} errors.
     * @param ex {@link MethodArgumentTypeMismatchException} exception
     * @param request {@link WebRequest}
     * @return Response entity with list of {@link ExceptionDto}.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<List<ExceptionDto>> handleMethodArgumentTypeMismatchException(Exception ex, WebRequest request) {
        ExceptionDto dto = new ExceptionDto();
        dto.setMessage("Invalid value");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(List.of(dto));
    }

    /**
     * Handler for {@link ValidationException} errors.
     * @param ex {@link ValidationException} exception
     * @param request {@link WebRequest}
     * @return Response entity with {@link ExceptionDto} object.
     */
    @ExceptionHandler(ValidationException.class)
    public final ResponseEntity<ExceptionDto> handleValidationException(Exception ex, WebRequest request) {
        ValidationException exception = (ValidationException) ex;

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionDto(exception.getMessage()));
    }
}
