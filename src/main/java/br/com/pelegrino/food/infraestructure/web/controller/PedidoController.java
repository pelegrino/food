package br.com.pelegrino.food.infraestructure.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.pelegrino.food.domain.pedido.Pedido;
import br.com.pelegrino.food.domain.pedido.PedidoRepository;

@Controller
@RequestMapping("/cliente/pedido")
public class PedidoController {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@GetMapping(path = "/view")
	public String viewPedido(
			@RequestParam("pedidoId") Integer pedidoId,
			Model model) {
		
		Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow();
		model.addAttribute("pedido", pedido);
		
		return "clientePedido";
	}

}
