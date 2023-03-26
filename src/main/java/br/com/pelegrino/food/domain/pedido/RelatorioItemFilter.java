package br.com.pelegrino.food.domain.pedido;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class RelatorioItemFilter {

	private Integer itemId;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataInicial;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataFinal;
}
