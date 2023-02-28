package br.com.pelegrino.food.domain.restaurante;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RestauranteRepository extends JpaRepository<Restaurante, Integer> {
	
	public Restaurante findByEmail(String Email);
	
	public List<Restaurante> findByNomeIgnoreCaseContaining(String nome);
	
	public List<Restaurante> findByCategorias_Id(Integer categoriaId);
	
}
