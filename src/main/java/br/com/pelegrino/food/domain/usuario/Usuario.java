package br.com.pelegrino.food.domain.usuario;

import java.io.Serializable;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SuppressWarnings("serial")
@MappedSuperclass
public class Usuario implements Serializable {
	
	@EqualsAndHashCode.Include
	@Id
	private Integer id;
	
	private String nome;
	
	private String email;
	
	private String senha;
	
	private String telefone;

}
