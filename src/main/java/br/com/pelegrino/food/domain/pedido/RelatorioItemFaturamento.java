package br.com.pelegrino.food.domain.pedido;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RelatorioItemFaturamento {

	private String nome;
	private Long quantidade;
	private BigDecimal valor;
}
