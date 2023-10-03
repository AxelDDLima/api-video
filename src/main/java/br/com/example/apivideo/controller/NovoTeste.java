package br.com.example.apivideo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v2/")
public class NovoTeste {
	@GetMapping("testet")
	public String teste() {
		return "Aqui";
	}
}
