package br.com.pelegrino.food.application.service;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import br.com.pelegrino.food.domain.pagamento.DadosCartao;
import br.com.pelegrino.food.domain.pagamento.StatusPagamento;
import br.com.pelegrino.food.domain.pedido.Carrinho;
import br.com.pelegrino.food.domain.pedido.ItemPedido;
import br.com.pelegrino.food.domain.pedido.ItemPedidoPK;
import br.com.pelegrino.food.domain.pedido.ItemPedidoRepository;
import br.com.pelegrino.food.domain.pedido.Pedido;
import br.com.pelegrino.food.domain.pedido.Pedido.Status;
import br.com.pelegrino.food.domain.pedido.PedidoRepository;
import br.com.pelegrino.food.util.SecurityUtils;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Value("${food.pay.url}")
	private String payUrl;
	
	@Value("${food.pay.token}")
	private String token;
	
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = PagamentoException.class)
	public Pedido criarEPagar(Carrinho carrinho, String numCartao) throws PagamentoException {
		
		Pedido pedido = new Pedido();
		pedido.setData(LocalDateTime.now());
		pedido.setCliente(SecurityUtils.loggedCliente());
		pedido.setRestaurante(carrinho.getRestaurante());
		pedido.setStatus(Status.Producao);
		pedido.setTaxaEntrega(carrinho.getRestaurante().getTaxaEntrega());
		pedido.setSubtotal(carrinho.getPrecoTotal(false));
		pedido.setTotal(carrinho.getPrecoTotal(true));
		
		pedidoRepository.save(pedido);
		
		int ordem = 1;
		
		for (ItemPedido itemPedido : carrinho.getItens()) {
			itemPedido.setId(new ItemPedidoPK(pedido, ordem++));
			itemPedidoRepository.save(itemPedido);
		}
		
		DadosCartao dadosCartao = new DadosCartao();
		dadosCartao.setNumCartao(numCartao);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Token", token);
		
		HttpEntity<DadosCartao> requestEntity = new HttpEntity<>(dadosCartao, headers);
		
		RestTemplate restTemplate = new RestTemplate();
		
		Map<String, String> response;
		
		try {
			response = restTemplate.postForObject(payUrl, requestEntity	, Map.class);
		
		} catch (Exception e) {
			throw new PagamentoException("Erro no servidor de pagamento.");
			
		}
			
		StatusPagamento statusPagamento = StatusPagamento.valueOf(response.get("status"));
			
			if(statusPagamento != StatusPagamento.Autorizado) {
				throw new PagamentoException(statusPagamento.getDescricao());
			}
			
		return pedido;
	}

}
