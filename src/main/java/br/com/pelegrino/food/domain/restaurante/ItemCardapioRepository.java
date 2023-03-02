package br.com.pelegrino.food.domain.restaurante;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemCardapioRepository extends JpaRepository<ItemCardapio, Integer> {
	
	public List<ItemCardapio> findByRestaurante_IdOrderByNome(Integer restauranteId);
	
	public List<ItemCardapio> findByRestaurante_IdAndDestaqueOrderByNome(Integer restauranteId, boolean destaque);
	
	public List<ItemCardapio> findByRestaurante_IdAndDestaqueAndCategoriaOrderByNome(Integer restauranteId, boolean destaque, String categoria);

	
	@Query("SELECT DISTINCT ic.categoria FROM ItemCardapio ic WHERE ic.restaurante.id = ?1 ORDER BY ic.categoria")
	public List<String> findCategorias(Integer restauranteId);
	
}
