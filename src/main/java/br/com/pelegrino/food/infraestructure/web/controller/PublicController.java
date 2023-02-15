package br.com.pelegrino.food.infraestructure.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.pelegrino.food.application.ClienteService;
import br.com.pelegrino.food.application.ValidationException;
import br.com.pelegrino.food.domain.cliente.Cliente;
import br.com.pelegrino.food.domain.restaurante.Restaurante;
import jakarta.validation.Valid;


@Controller
@RequestMapping(path = "/public")
public class PublicController {
	
	@Autowired
	private ClienteService clienteService;
	
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

}
