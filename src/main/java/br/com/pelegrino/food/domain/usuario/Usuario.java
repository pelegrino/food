package br.com.pelegrino.food.domain.usuario;

import java.io.Serializable;

import br.com.pelegrino.food.util.StringUtils;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
	@Pattern(regexp = "[0-9]{10,11}", message = "O telefone possui formato inválido")
	@Size(max = 80, message = "O nome é muito grande")
	@Column(length = 11, nullable = false)
	private String telefone;
	
	public void encryptPassword() {
		this.senha = StringUtils.encrypt(this.senha);
	}

}
