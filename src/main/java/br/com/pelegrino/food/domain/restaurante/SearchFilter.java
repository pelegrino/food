package br.com.pelegrino.food.domain.restaurante;

import lombok.Data;

@Data
public class SearchFilter {
	
	public enum SearchType {
		Texto, Categoria
	}

	private String texto;
	private SearchType searchType;
	private Integer categoriaId;
	
	
	public void processFilter() {
		if (searchType == SearchType.Texto) {
			categoriaId = null;
			
		} else if (searchType == SearchType.Categoria) {
			texto = null;
		}
	}
}
