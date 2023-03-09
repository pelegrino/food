package br.com.pelegrino.food.infraestructure.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import br.com.pelegrino.food.domain.pedido.Carrinho;
import br.com.pelegrino.food.domain.pedido.RestauranteDiferenteException;
import br.com.pelegrino.food.domain.restaurante.ItemCardapio;
import br.com.pelegrino.food.domain.restaurante.ItemCardapioRepository;

@Controller
@RequestMapping("/cliente/carrinho")
@SessionAttributes("carrinho")
public class CarrinhoController {
	
	@Autowired
	private ItemCardapioRepository itemCardapioRepository;
	
	@ModelAttribute("carrinho")
	public Carrinho carrinho() {
		return new Carrinho();
	}
	
	@GetMapping(path = "/visualizar")
	public String viewCarrinho() {
		return "clienteCarrinho";
	}
	
	@GetMapping(path = "/adicionar")
	public String adicionarItem(
			@RequestParam("itemId") Integer itemId,
			@RequestParam("quantidade") Integer quantidade,
			@RequestParam("observacoes") String observacoes,
			@ModelAttribute("carrinho") Carrinho carrinho,
			Model model) {
		
		ItemCardapio itemCardapio = itemCardapioRepository.findById(itemId).orElseThrow();
		
		try {
			carrinho.adicionarItem(itemCardapio, quantidade, observacoes);
			
		} catch (RestauranteDiferenteException e) {
			model.addAttribute("msg", "Não é possível misturar comidas de restaurantes diferentes.");
			
		}
		
		return "clienteCarrinho";
	}
	
	@GetMapping(path = "/remover")
	public String removerItem(
			@RequestParam("itemId") Integer itemId,
			@ModelAttribute("carrinho") Carrinho carrinho,
			SessionStatus sessionStatus,
			Model model) {
		
		ItemCardapio itemCardapio = itemCardapioRepository.findById(itemId).orElseThrow();
		
		carrinho.removerItem(itemCardapio);
		
		if (carrinho.vazio()) {
			sessionStatus.setComplete();
			
		}
		
		return "clienteCarrinho";

	}
	
}
