package br.com.pelegrino.food.domain.pedido;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.pelegrino.food.domain.restaurante.ItemCardapio;
import br.com.pelegrino.food.domain.restaurante.Restaurante;

public class Carrinho {
	
	private List<ItemPedido> itens = new ArrayList<>();
	private Restaurante restaurante;
	
	public void adicionarItem(ItemCardapio itemCardapio, Integer quantidade, String observacoes) throws RestauranteDiferenteException {
		
		if (itens.size() == 0) {
			restaurante = itemCardapio.getRestaurante();
			
		} else if (!itemCardapio.getRestaurante().getId().equals(restaurante.getId())) {
			throw new RestauranteDiferenteException();
		}
		
		if (!exists(itemCardapio)) {
			ItemPedido itemPedido = new ItemPedido();
			itemPedido.setItemCardapio(itemCardapio);
			itemPedido.setQuantidade(quantidade);
			itemPedido.setObservacoes(observacoes);
			itemPedido.setPreco(itemCardapio.getPreco());
			itens.add(itemPedido);
		}
	}
	
	
	public void adicionarItem(ItemPedido itemPedido) {
		try {
			adicionarItem(itemPedido.getItemCardapio(), itemPedido.getQuantidade(), itemPedido.getObservacoes());
		} catch (RestauranteDiferenteException e) {
			e.printStackTrace();
		}
	}
	
	public void removerItem(ItemCardapio itemCardapio) {
		for (Iterator<ItemPedido> iterator = itens.iterator(); iterator.hasNext();) {
			ItemPedido itemPedido = iterator.next();
			if (itemPedido.getItemCardapio().getId().equals(itemCardapio.getId())) {
				iterator.remove();
				break;
			}
		}
		
		if (itens.size() == 0) {
			restaurante = null;
		}
	}
	
	
	private boolean exists(ItemCardapio itemCardapio) {
		for (ItemPedido itemPedido : itens) {
			if (itemPedido.getItemCardapio().getId().equals(itemCardapio.getId())) {
				return true;
			}
		}
		return false;
	}
}
