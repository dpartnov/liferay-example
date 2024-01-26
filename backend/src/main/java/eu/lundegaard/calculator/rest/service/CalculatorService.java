package eu.lundegaard.calculator.rest.service;

/**
 * Calculator service interface, contains function definitions for calculator operations.
 *
 * @author Denys Partnov, denys.partnov@lundegaard.eu, 2022
 */
public interface CalculatorService {

    /**
     * Calculate monthly payment for loan amount, months count and annual interest rate.
     *
     * @param loanAmount loan amount
     * @param months months
     * @param annualInterestRate annual interest rate
     * @return monthly payment amount
     */
    int calculate(Integer loanAmount, Integer months, Double annualInterestRate);
}
