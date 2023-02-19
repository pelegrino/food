package br.com.pelegrino.food.application.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.pelegrino.food.domain.cliente.Cliente;
import br.com.pelegrino.food.domain.cliente.ClienteRepository;
import br.com.pelegrino.food.domain.restaurante.CategoriaRestaurante;
import br.com.pelegrino.food.domain.restaurante.CategoriaRestauranteRepository;
import br.com.pelegrino.food.domain.restaurante.Restaurante;
import br.com.pelegrino.food.domain.restaurante.RestauranteRepository;
import br.com.pelegrino.food.util.StringUtils;

@Component
public class InsertDataForTesting {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CategoriaRestauranteRepository categoriaRestauranteRepository;

	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {
		clientes();
		Restaurante[] restaurantes = restaurantes();
		
	}
	
	private Restaurante[] restaurantes() {
		List<Restaurante> restaurantes = new ArrayList<>();
		
		CategoriaRestaurante categoriaPizza = categoriaRestauranteRepository.findById(1).orElseThrow();
		CategoriaRestaurante categoriaSanduiche = categoriaRestauranteRepository.findById(2).orElseThrow();
		CategoriaRestaurante categoriaSobremesa = categoriaRestauranteRepository.findById(5).orElseThrow();
		CategoriaRestaurante categoriaJapones = categoriaRestauranteRepository.findById(6).orElseThrow();
		
		Restaurante r = new Restaurante();
		r.setNome("Bubger King");
		r.setEmail("r1@food.com.br");
		r.setSenha(StringUtils.encrypt("r"));
		r.setCnpj("10000000000000");
		r.setTaxaEntrega(BigDecimal.valueOf(3.2));
		r.setTempoEntrega(30);
		r.setTelefone("9876543210");
		r.getCategorias().add(categoriaSanduiche);
		r.getCategorias().add(categoriaSobremesa);
		r.setLogotipo("0001-logo.png");
		restauranteRepository.save(r);
		restaurantes.add(r);
		
		
		r = new Restaurante();
		r.setNome("Mc Naldo´s");
		r.setEmail("r2@food.com.br");
		r.setSenha(StringUtils.encrypt("r"));
		r.setCnpj("11000000000000");
		r.setTaxaEntrega(BigDecimal.valueOf(4.5));
		r.setTempoEntrega(25);
		r.setTelefone("9999999999");
		r.getCategorias().add(categoriaSanduiche);
		r.getCategorias().add(categoriaSobremesa);
		r.setLogotipo("0002-logo.png");
		restauranteRepository.save(r);
		restaurantes.add(r);
		
		
		r = new Restaurante();
		r.setNome("Sbubby");
		r.setEmail("r3@food.com.br");
		r.setSenha(StringUtils.encrypt("r"));
		r.setCnpj("11100000000000");
		r.setTaxaEntrega(BigDecimal.valueOf(12.2));
		r.setTempoEntrega(38);
		r.setTelefone("9999999990");
		r.getCategorias().add(categoriaSanduiche);
		r.getCategorias().add(categoriaSobremesa);
		r.setLogotipo("0003-logo.png");
		restauranteRepository.save(r);
		restaurantes.add(r);
		
		
		r = new Restaurante();
		r.setNome("Pizza Brut");
		r.setEmail("r4@food.com.br");
		r.setSenha(StringUtils.encrypt("r"));
		r.setCnpj("11110000000000");
		r.setTaxaEntrega(BigDecimal.valueOf(9.8));
		r.setTempoEntrega(22);
		r.setTelefone("9999999000");
		r.getCategorias().add(categoriaPizza);
		r.getCategorias().add(categoriaSobremesa);
		r.setLogotipo("0003-logo.png");
		restauranteRepository.save(r);
		restaurantes.add(r);
		
		
		r = new Restaurante();
		r.setNome("Wiki Japa");
		r.setEmail("r5@food.com.br");
		r.setSenha(StringUtils.encrypt("r"));
		r.setCnpj("11111000000000");
		r.setTaxaEntrega(BigDecimal.valueOf(14.9));
		r.setTempoEntrega(19);
		r.setTelefone("9999990000");
		r.getCategorias().add(categoriaJapones);
		r.getCategorias().add(categoriaSobremesa);
		r.setLogotipo("0003-logo.png");
		restauranteRepository.save(r);
		restaurantes.add(r);
		
		Restaurante[] array = new Restaurante[restaurantes.size()];
		return restaurantes.toArray(array);
	}
	
	private Cliente[] clientes() {
		List<Cliente> clientes = new ArrayList<>();
		
		Cliente	c = new Cliente();
		c.setNome("João Silva");
		c.setEmail("joao@food.com.br");
		c.setSenha(StringUtils.encrypt("c"));
		c.setCep("00000000");
		c.setCpf("00000000000");
		c.setTelefone("0000000000");
		clienteRepository.save(c);
		
		
		c = new Cliente();
		c.setNome("Maria Torres");
		c.setEmail("maria@food.com.br");
		c.setSenha(StringUtils.encrypt("c"));
		c.setCep("10000000");
		c.setCpf("10000000000");
		c.setTelefone("1000000000");
		clienteRepository.save(c);
		
		Cliente[] array = new Cliente[clientes.size()];
		return clientes.toArray(array);
	}
}
