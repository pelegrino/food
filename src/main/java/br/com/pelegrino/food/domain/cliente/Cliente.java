package br.com.pelegrino.food.domain.cliente;

import br.com.pelegrino.food.domain.usuario.Usuario;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
@SuppressWarnings("serial")
public class Cliente extends Usuario {

	private String cpf;
	private String cep;
	
}
