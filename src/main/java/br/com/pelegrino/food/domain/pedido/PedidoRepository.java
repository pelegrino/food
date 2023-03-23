package br.com.pelegrino.food.domain.pedido;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
	
	@Query("SELECT p FROM Pedido p WHERE p.cliente.id = ?1 ORDER BY p.data DESC")
	public List<Pedido> listaPedidosByCliente(Integer clienteId);

	//Forma simplificada abaixo
	//public List<Pedido> findByCliente_Id(Integer clienteId);
	
	public List<Pedido> findByRestaurante_IdOrderByDataDesc(Integer restauranteId);
}
