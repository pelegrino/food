package br.com.pelegrino.food.domain.usuario;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "O nome não pode ser vazio")
	@Size(max = 80, message = "O nome é muito grande")
	private String nome;

	@NotBlank(message = "O e-mail não pode ser vazio")
	@Size(max = 60, message = "O e-mail é muito grande")
	@Email(message = "O email é inválido")
	private String email;
	
	@NotBlank(message = "A senha não pode ser vazio")
	@Size(max = 80, message = "O senha é muito grande")
	private String senha;
	
	@NotBlank(message = "O telefone não pode ser vazio")
	@Pattern(regexp = "[0-9]{10, 11}", message = "O telefone possui formato inválido")
	@Size(max = 80, message = "O nome é muito grande")
	@Column(length = 11, nullable = false)
	private String telefone;

}
