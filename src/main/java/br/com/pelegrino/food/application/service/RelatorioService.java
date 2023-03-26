package br.com.pelegrino.food.application.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pelegrino.food.domain.pedido.Pedido;
import br.com.pelegrino.food.domain.pedido.PedidoRepository;
import br.com.pelegrino.food.domain.pedido.RelatorioItemFaturamento;
import br.com.pelegrino.food.domain.pedido.RelatorioItemFilter;
import br.com.pelegrino.food.domain.pedido.RelatorioPedidoFilter;

@Service
public class RelatorioService {
	
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	public List<Pedido> listPedidos(Integer restauranteId, RelatorioPedidoFilter filter) {
		
		Integer pedidoId = filter.getPedidoId();
		
		if(pedidoId != null) {
			Pedido pedido = pedidoRepository.findByIdAndRestaurante_Id(pedidoId, restauranteId);
			return List.of(pedido);
		}
		
		LocalDate dataInicial = filter.getDataInicial();
		LocalDate dataFinal = filter.getDataFinal();
		
		if (dataInicial == null) {
			return List.of();
		
		}
		
		if (dataFinal == null) {
			dataFinal = LocalDate.now();
		}
		
		return pedidoRepository.findByDataInterval(restauranteId, dataInicial.atStartOfDay(), dataFinal.atTime(23, 59, 59));
		
	}
	
	public <BigDecimal> List<RelatorioItemFaturamento> calcularFaturamentoItens(Integer restauranteId, RelatorioItemFilter filter) {	
		List<Object[]> itensObj;
		
		Integer itemId = filter.getItemId();
		LocalDate dataInicial = filter.getDataInicial();
		LocalDate dataFinal = filter.getDataFinal();
		
		if (dataInicial == null) {
			return List.of();
		
		}
		
		if (dataFinal == null) {
			dataFinal = LocalDate.now();
		}
		
		LocalDateTime dataHoraInicial = dataInicial.atStartOfDay();
		LocalDateTime dataHoraFinal = dataFinal.atTime(23, 59, 59);
		
		if (itemId != 0) {
			itensObj = pedidoRepository.findItensForFaturamento(restauranteId, itemId, dataHoraInicial, dataHoraFinal);

		} else {
			itensObj = pedidoRepository.findItensForFaturamento(restauranteId, dataHoraInicial, dataHoraFinal);
			
		}
		
		List<RelatorioItemFaturamento> itens = new ArrayList<>();
		
		for (Object[] item : itensObj) {
			String nome = (String) item[0];
			Long quantidade = (Long) item[1];
			@SuppressWarnings("unchecked")
			BigDecimal valor = (BigDecimal) item[2];
			itens.add(new RelatorioItemFaturamento(nome, quantidade, (java.math.BigDecimal) valor));
		}
		
		return itens;
	}
	
}
