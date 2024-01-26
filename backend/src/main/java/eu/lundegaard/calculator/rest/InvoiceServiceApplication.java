package eu.lundegaard.calculator.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Spring boot main
 * @author Denys Partnov, denys.partnov@lundegaard.eu, 2022
 */
@SpringBootApplication
@EnableWebMvc
public class InvoiceServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvoiceServiceApplication.class, args);
	}

}
