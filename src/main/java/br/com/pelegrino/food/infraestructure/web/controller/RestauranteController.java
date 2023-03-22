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

import br.com.pelegrino.food.application.service.RestauranteService;
import br.com.pelegrino.food.application.service.ValidationException;
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
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CategoriaRestauranteRepository categoriaRestauranteRepository;
	
	@Autowired
	private ItemCardapioRepository itemCardapioRepository;

	@GetMapping(path = "/home")
	public String home() {
		return "restauranteHome";
	}
	
	@GetMapping(path = "/edit")
	public String edit(Model model) {
		Integer RestauranteId = SecurityUtils.loggedRestaurante().getId();
		Restaurante restaurante = restauranteRepository.findById(RestauranteId).orElseThrow();
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
		Integer RestauranteId = SecurityUtils.loggedRestaurante().getId();
		Restaurante restaurante = restauranteRepository.findById(RestauranteId).orElseThrow();
		model.addAttribute("restaurante", restaurante);
		
		List<ItemCardapio> itensCardapio = itemCardapioRepository.findByRestaurante_IdOrderByNome(RestauranteId);
		
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
			Integer RestauranteId = SecurityUtils.loggedRestaurante().getId();
			Restaurante restaurante = restauranteRepository.findById(RestauranteId).orElseThrow();
			model.addAttribute("restaurante", restaurante);
			
			List<ItemCardapio> itensCardapio = itemCardapioRepository.findByRestaurante_IdOrderByNome(RestauranteId);
			model.addAttribute("itensCardapio", itensCardapio);
			
			return "restauranteComidas";
		}
		
		restauranteService.saveItemCardapio(itemCardapio);
		return "redirect:/restaurante/comidas";
	}
}
