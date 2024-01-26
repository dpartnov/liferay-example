package eu.lundegaard.calculator.rest.service;

import eu.lundegaard.calculator.rest.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Calculation service implementation.
 *
 * @author Denys Partnov, denys.partnov@lundegaard.eu, 2022
 */
@Service
@Slf4j
public class CalculatorServiceImpl implements CalculatorService {

    @Override
    public int calculate(Integer loanAmount, Integer months, Double annualInterestRate) {

        if (loanAmount == null || loanAmount <= 0) {
            throw new ValidationException("loanAmount must be greater than zero");
        }

        if (months == null || months <= 0) {
            throw new ValidationException("months must be greater than zero");
        }

        if (annualInterestRate == null || annualInterestRate <= 0) {
            throw new ValidationException("annualInterestRate must be greater than zero");
        }


        // calculation by: https://poradna.finance.cz/bydleni/43758-vzorec-na-vypocet-splatky-uveru/
        double monthsInterestRate = annualInterestRate / 1200.0;
        double num = Math.pow(1.0 + monthsInterestRate, months.doubleValue());
        double result = (loanAmount.doubleValue() * monthsInterestRate * num) / (num - 1.0);
        return (int) Math.round(result);
    }
}
