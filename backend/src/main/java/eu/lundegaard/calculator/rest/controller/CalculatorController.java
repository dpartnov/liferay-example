package eu.lundegaard.calculator.rest.controller;

import eu.lundegaard.calculator.rest.dto.ResultDto;
import eu.lundegaard.calculator.rest.service.CalculatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for calculator.
 *
 * @author Denys Partnov, denys.partnov@lundegaard.eu, 2022
 */
@RestController
@RequestMapping("/calculator")
@Slf4j
public class CalculatorController {

    private final CalculatorService service;

    public CalculatorController(CalculatorService service) {
        this.service = service;
    }

    /**
     * Calculate function
     * @param loanAmount loan amount
     * @param months months
     * @param annualRate annual interest rate
     * @return {@link ResultDto} object with monthly payment amount value
     */
    @PostMapping()
    public ResponseEntity<Integer> calculate(@RequestParam(value = "loanAmount") Integer loanAmount,
                                                    @RequestParam(value = "months") Integer months,
                                                    @RequestParam(value = "annualRate") Double annualRate) {

        return ResponseEntity.ok(service.calculate(loanAmount, months, annualRate));
    }
}
