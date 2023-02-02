package br.com.pelegrino.food.domain.cliente;

import br.com.pelegrino.food.domain.usuario.Usuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cliente extends Usuario {

	private String cpf;
	private String cep;
	
}
