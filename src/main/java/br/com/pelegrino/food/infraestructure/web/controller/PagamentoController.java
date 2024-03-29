package br.com.pelegrino.food.infraestructure.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import br.com.pelegrino.food.application.service.PagamentoException;
import br.com.pelegrino.food.application.service.PedidoService;
import br.com.pelegrino.food.domain.pedido.Carrinho;
import br.com.pelegrino.food.domain.pedido.Pedido;

@Controller
@RequestMapping("/cliente/pagamento")
@SessionAttributes("carrinho")
public class PagamentoController {
	
	@Autowired
	private PedidoService pedidoService;
	
	@PostMapping(path = "/pagar")
	public String pagar(
			@RequestParam("numCartao") String numCartao,
			@ModelAttribute("carrinho") Carrinho carrinho,
			SessionStatus sessionStatus,
			Model model) {
		
		try {
		Pedido pedido = pedidoService.criarEPagar(carrinho, numCartao);
		sessionStatus.setComplete();
		
		return "redirect:/cliente/pedido/view?pedidoId=" + pedido.getId();
		
		} catch (PagamentoException e) {
			model.addAttribute("msg", e.getMessage());
			return "clienteCarrinho";
		}

	}

}
