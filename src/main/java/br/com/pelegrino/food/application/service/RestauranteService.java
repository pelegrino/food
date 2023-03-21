package br.com.pelegrino.food.application.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pelegrino.food.domain.restaurante.Restaurante;
import br.com.pelegrino.food.domain.restaurante.RestauranteComparator;
import br.com.pelegrino.food.domain.restaurante.RestauranteRepository;
import br.com.pelegrino.food.domain.restaurante.SearchFilter;
import br.com.pelegrino.food.domain.restaurante.SearchFilter.SearchType;
import br.com.pelegrino.food.util.SecurityUtils;

@Service
public class RestauranteService {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private ImageService imageService;
	
	@Transactional
	public void saveRestaurante(Restaurante restaurante) throws ValidationException {
		
		if (!validateEmail(restaurante.getEmail(), restaurante.getId())) {
			throw new ValidationException("O e-mail está duplicado.");
		}
		
		if (restaurante.getId() != null) {
			Restaurante restauranteDB = restauranteRepository.findById(restaurante.getId()).orElseThrow();
			restaurante.setSenha(restauranteDB.getSenha());
			restaurante.setLogotipo(restauranteDB.getLogotipo());
			restauranteRepository.save(restaurante);
			
		} else {
			restaurante.encryptPassword();
			restaurante = restauranteRepository.save(restaurante);
			restaurante.setLogotipoFileName();
			imageService.uploadLogotipo(restaurante.getLogotipoFile(), restaurante.getLogotipo());
		}
		
		
		restauranteRepository.save(restaurante);
	}
	
	private boolean validateEmail(String email, Integer id) {
		
		Restaurante restaurante = restauranteRepository.findByEmail(email);
		
		if(restaurante != null) {
			if(id == null) {
				return false;
			} 
			
			if(!restaurante.getId().equals(id)) {
				return false;
			}
		}
		
		return true;
	}
	
	public List<Restaurante> search(SearchFilter filter) {
		List<Restaurante> restaurantes;
		
		if (filter.getSearchType() == SearchType.Texto) {
			restaurantes = restauranteRepository.findByNomeIgnoreCaseContaining(filter.getTexto());
			
		} else if (filter.getSearchType() == SearchType.Categoria) {
			restaurantes = restauranteRepository.findByCategorias_Id(filter.getCategoriaId());
			
		} else {
			throw new IllegalStateException("O tipo de busca " + filter.getSearchType() + " não é suportado.");
		}
		
		Iterator<Restaurante> it = restaurantes.iterator();
		
		while (it.hasNext()) {
			Restaurante restaurante = it.next();
			double taxaEntrega = restaurante.getTaxaEntrega().doubleValue();
			
			if (filter.isEntregaGratis() && taxaEntrega > 0
					|| !filter.isEntregaGratis() && taxaEntrega == 0) {
				it.remove();
			}
			
		}
		
		RestauranteComparator comparator = new RestauranteComparator(filter, SecurityUtils.loggedCliente().getCep());
		restaurantes.sort(comparator);
		
		return restaurantes;
	}

}