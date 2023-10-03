package br.com.example.apivideo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication
@OpenAPIDefinition
public class ApiVideoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiVideoApplication.class, args);
	}

}
