package br.com.pelegrino.food.infraestructure.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import br.com.pelegrino.food.domain.restaurante.CategoriaRestauranteRepository;
import br.com.pelegrino.food.domain.restaurante.Restaurante;
import javax.validation.Valid;


@Controller
@RequestMapping(path = "/public")
public class PublicController {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private CategoriaRestauranteRepository categoriaRestauranteRepository;
	
	@GetMapping("/cliente/new")
	public String newCliente(Model model) {
		model.addAttribute("cliente", new Cliente());
		ControllerHelper.setEditMode(model, false);
		return "clienteCadastro";
	}
	
	@GetMapping("/restaurante/new")
	public String newRestaurante(Model model) {
		model.addAttribute("restaurante", new Restaurante());
		ControllerHelper.setEditMode(model, false);
		ControllerHelper.addCategoriasToRequest(categoriaRestauranteRepository, model);
		return "restauranteCadastro";
	}
	
	@PostMapping(path = "/cliente/save")
	public String saveCliente(@ModelAttribute("cliente") @Valid Cliente cliente,
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
		ControllerHelper.setEditMode(model, false);
		return "clienteCadastro";
	}
	
	@PostMapping(path = "/restaurante/save")
	public String saveRestaurante(@ModelAttribute("restaurante") @Valid Restaurante restaurante,
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
		ControllerHelper.setEditMode(model, false);
		ControllerHelper.addCategoriasToRequest(categoriaRestauranteRepository, model);
		return "restauranteCadastro";
	}

}
