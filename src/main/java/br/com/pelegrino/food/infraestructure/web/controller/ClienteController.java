package br.com.pelegrino.food.infraestructure.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.pelegrino.food.application.service.ClienteService;
import br.com.pelegrino.food.application.service.RestauranteService;
import br.com.pelegrino.food.application.service.ValidationException;
import br.com.pelegrino.food.domain.cliente.Cliente;
import br.com.pelegrino.food.domain.cliente.ClienteRepository;
import br.com.pelegrino.food.domain.restaurante.CategoriaRestaurante;
import br.com.pelegrino.food.domain.restaurante.CategoriaRestauranteRepository;
import br.com.pelegrino.food.domain.restaurante.Restaurante;
import br.com.pelegrino.food.domain.restaurante.SearchFilter;
import br.com.pelegrino.food.util.SecurityUtils;

@Controller
@RequestMapping(path = "/cliente")
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CategoriaRestauranteRepository categoriaRestauranteRepository;
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping(path = "/home")
	public String home(Model model) {
		List<CategoriaRestaurante> categorias = categoriaRestauranteRepository.findAll(Sort.by("nome"));
		model.addAttribute("categorias", categorias);
		model.addAttribute("searchFilter", new SearchFilter());
		return "clienteHome";
	}
		
	@GetMapping(path = "/edit")
	public String edit(Model model) {
		Integer clienteId = SecurityUtils.loggedCliente().getId();
		Cliente cliente = clienteRepository.findById(clienteId).orElseThrow();
		model.addAttribute("cliente", cliente);
		ControllerHelper.setEditMode(model, true);
		
		return "clienteCadastro";
	}
	
	@PostMapping("/save")
	public String save(@ModelAttribute("cliente") @Valid Cliente cliente,
			Errors errors,
			Model model) {
		
		if (!errors.hasErrors()) {
			try {
			clienteService.saveCliente(cliente);
			model.addAttribute("msg", "Cliente salvo com sucesso!");
		} catch (ValidationException e) {
			errors.rejectValue("email", null, e.getMessage());
		}
		
		}
		ControllerHelper.setEditMode(model, true);
		return "clienteCadastro";
	}
	
	@GetMapping(path = "/search")
	public String search(
			@ModelAttribute("searchFilter") SearchFilter filter,
			Model model) {
		
		filter.processFilter();
		
		List<Restaurante> restaurantes = restauranteService.search(filter);
		model.addAttribute("restaurantes", restaurantes);
		ControllerHelper.addCategoriasToRequest(categoriaRestauranteRepository, model);
		
		model.addAttribute("searchFilter", filter);
		
		return "clienteBusca";
	}
}
