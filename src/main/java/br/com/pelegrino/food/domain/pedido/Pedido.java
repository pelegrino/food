package br.com.pelegrino.food.domain.pedido;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.pelegrino.food.domain.cliente.Cliente;
import br.com.pelegrino.food.domain.pagamento.Pagamento;
import br.com.pelegrino.food.domain.restaurante.Restaurante;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "pedido")
public class Pedido implements Serializable {
	
	public enum Status {
		Producao(1, "Em produção.", false),
		Entrega(2, "Saiu para entrega.", false),
		Concluido(3, "Entregue.", true);
		
		Status (int ordem, String descricao, boolean ultimo) {
			this.ordem = ordem;
			this.descricao = descricao;
			this.ultimo = ultimo;
		}
		
		int ordem;
		String descricao;
		boolean ultimo;
		
		public String getDescricao() {
			return descricao;
		}
		
		public int getOrdem() {
			return ordem;
		}
		
		public boolean isUltimo() {
			return ultimo;
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	private LocalDateTime data;
	
	@NotNull
	private Status status;
	
	@NotNull
	@ManyToOne
	private Cliente cliente;
	
	@NotNull
	@ManyToOne
	private Restaurante restaurante;
	
	@NotNull
	private BigDecimal subtotal;
	
	@NotNull
	@Column(name = "taxaEntrega")
	private BigDecimal taxaEntrega;
	
	@NotNull
	private BigDecimal total;
	
	@OneToOne(mappedBy = "pedido")
	private Pagamento pagamento;
	
	@OneToMany(mappedBy = "id.pedido", fetch = FetchType.EAGER)
	private Set<ItemPedido> itensPedido;
	
	public String getFormattedId() {
		return String.format("#%05d", id);
	}
}
