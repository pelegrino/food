package br.com.pelegrino.food.domain.pedido;

import java.math.BigDecimal;

import br.com.pelegrino.food.domain.restaurante.ItemCardapio;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemPedido {
	
	@EqualsAndHashCode.Include
	private ItemCardapio itemCardapio;
	
	private Integer quantidade;
	
	private String observacoes;
	
	private BigDecimal preco;

}
