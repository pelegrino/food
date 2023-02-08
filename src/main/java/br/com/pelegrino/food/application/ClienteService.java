package br.com.pelegrino.food.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pelegrino.food.domain.cliente.Cliente;
import br.com.pelegrino.food.domain.cliente.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public void saveCliente(Cliente cliente) {
		clienteRepository.save(cliente);
	}

}