package eu.lundegaard.calculator.rest.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO object to transfer error message in ResponseEntity.
 * @author Denys Partnov, denys.partnov@lundegaard.eu, 2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionDto {

    private String message;
}
