package br.com.pelegrino.food.infraestructure.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.pelegrino.food.application.service.RelatorioService;
import br.com.pelegrino.food.application.service.RestauranteService;
import br.com.pelegrino.food.application.service.ValidationException;
import br.com.pelegrino.food.domain.pedido.Pedido;
import br.com.pelegrino.food.domain.pedido.PedidoRepository;
import br.com.pelegrino.food.domain.pedido.RelatorioItemFaturamento;
import br.com.pelegrino.food.domain.pedido.RelatorioItemFilter;
import br.com.pelegrino.food.domain.pedido.RelatorioPedidoFilter;
import br.com.pelegrino.food.domain.restaurante.CategoriaRestauranteRepository;
import br.com.pelegrino.food.domain.restaurante.ItemCardapio;
import br.com.pelegrino.food.domain.restaurante.ItemCardapioRepository;
import br.com.pelegrino.food.domain.restaurante.Restaurante;
import br.com.pelegrino.food.domain.restaurante.RestauranteRepository;
import br.com.pelegrino.food.util.SecurityUtils;

@Controller
@RequestMapping(path = "/restaurante")
public class RestauranteController {
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private RelatorioService relatorioService;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CategoriaRestauranteRepository categoriaRestauranteRepository;
	
	@Autowired
	private ItemCardapioRepository itemCardapioRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;

	
	@GetMapping(path = "/home")
	public String home(Model model) {
		Integer restauranteId = SecurityUtils.loggedRestaurante().getId();
		List<Pedido> pedidos = pedidoRepository.findByRestaurante_IdOrderByDataDesc(restauranteId);
		model.addAttribute("pedidos", pedidos);
		
		return "restauranteHome";
	}
	
	@GetMapping(path = "/edit")
	public String edit(Model model) {
		Integer restauranteId = SecurityUtils.loggedRestaurante().getId();
		Restaurante restaurante = restauranteRepository.findById(restauranteId).orElseThrow();
		model.addAttribute("restaurante", restaurante);
		ControllerHelper.setEditMode(model, true);
		ControllerHelper.addCategoriasToRequest(categoriaRestauranteRepository, model);
		
		return "restauranteCadastro";
	}
	
	@PostMapping("/save")
	public String save(@ModelAttribute("restaurante") @Valid Restaurante restaurante,
			Errors errors,
			Model model) {
		
		if (!errors.hasErrors()) {
			try {
			restauranteService.saveRestaurante(restaurante);
			model.addAttribute("msg", "Restaurante salvo com sucesso!");
		} catch (ValidationException e) {
			errors.rejectValue("email", null, e.getMessage());
		}
		
		}
		ControllerHelper.setEditMode(model, true);
		return "restauranteCadastro";
	}
	
	@GetMapping(path = "/comidas")
	public String viewComidas(Model model) {
		Integer restauranteId = SecurityUtils.loggedRestaurante().getId();
		Restaurante restaurante = restauranteRepository.findById(restauranteId).orElseThrow();
		model.addAttribute("restaurante", restaurante);
		
		List<ItemCardapio> itensCardapio = itemCardapioRepository.findByRestaurante_IdOrderByNome(restauranteId);
		
		model.addAttribute("itensCardapio", itensCardapio);
		model.addAttribute("itemCardapio", new ItemCardapio());
		
		return "restauranteComidas";
	}
	
	@GetMapping(path = "/comidas/remover")
	public String remover(@RequestParam("itemId") Integer itemId, Model model) {
		itemCardapioRepository.deleteById(itemId);
		
		return "redirect:/restaurante/comidas";
	}
		
	@PostMapping(path = "/comidas/cadastrar")
	public String cadastrar(
			@Valid @ModelAttribute("itemCardapio") ItemCardapio itemCardapio, 
			Errors errors, 
			Model model) {
		
		if (errors.hasErrors()) {
			Integer restauranteId = SecurityUtils.loggedRestaurante().getId();
			Restaurante restaurante = restauranteRepository.findById(restauranteId).orElseThrow();
			model.addAttribute("restaurante", restaurante);
			
			List<ItemCardapio> itensCardapio = itemCardapioRepository.findByRestaurante_IdOrderByNome(restauranteId);
			model.addAttribute("itensCardapio", itensCardapio);
			
			return "restauranteComidas";
		}
		
		restauranteService.saveItemCardapio(itemCardapio);
		return "redirect:/restaurante/comidas";
	}
	
	@GetMapping(path = "/pedido")
	public String viewPedido(
			@RequestParam("pedidoId") Integer pedidoId,
			Model model) {
		
		Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow();
		model.addAttribute("pedido", pedido);
		
		return "restaurantePedido";
	}
	
	@PostMapping(path = "/pedido/proximoStatus")
	public String proximoStatus(
			@RequestParam("pedidoId") Integer pedidoId, 
			Model model) {
		
		Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow();
		pedido.definirProximoStatus();
		pedidoRepository.save(pedido);
		
		
		model.addAttribute("pedido", pedido);
		model.addAttribute("msg", "Status alterado com sucesso");
		
		return "restaurantePedido";
	}
	
	@GetMapping(path = "/relatorio/pedidos")
	public String relatorioPedidos(
			@ModelAttribute("relatorioPedidoFilter") RelatorioPedidoFilter filter,
			Model model) {
		
		Integer restauranteId = SecurityUtils.loggedRestaurante().getId();
		List<Pedido> pedidos = relatorioService.listPedidos(restauranteId, filter);
		model.addAttribute("pedidos", pedidos);
		
		model.addAttribute("filter", filter);
		
		return "restauranteRelatorioPedidos";
	}
	
	@GetMapping(path = "/relatorio/itens")
	public String relatorioItens(
			@ModelAttribute("relatorioItemFilter") RelatorioItemFilter filter,
			Model model) {
		Integer restauranteId = SecurityUtils.loggedRestaurante().getId();
		List<ItemCardapio> itensCardapio = itemCardapioRepository.findByRestaurante_IdOrderByNome(restauranteId);
		model.addAttribute("itensCardapio", itensCardapio);
		
		List<RelatorioItemFaturamento> itensCalculados = relatorioService.calcularFaturamentoItens(restauranteId, filter);
		model.addAttribute("itensCalculados", itensCalculados);
		
		model.addAttribute("relatorioItemFilter", filter);
			
		return "restauranteRelatorioItens";
	}
	
}
