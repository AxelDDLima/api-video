package br.com.example.apivideo.exeception;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@NoArgsConstructor
@Setter @Getter
@AllArgsConstructor
public class RecursoNaoEncontrado extends RuntimeException{

	private String codigoErro ;
	private String mensagemErro ;

}
