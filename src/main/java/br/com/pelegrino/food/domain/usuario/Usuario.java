package br.com.pelegrino.food.domain.usuario;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {
	
	private Integer id;
	private String nome;
	private String email;
	private String senha;
	private String telefone;

}
