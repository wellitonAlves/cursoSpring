package com.welliton.estudonelioalves.estudoSpring.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welliton.estudonelioalves.estudoSpring.domain.Pedido;
import com.welliton.estudonelioalves.estudoSpring.repository.PedidoRepository;
import com.welliton.estudonelioalves.estudoSpring.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;

	public Pedido buscar(Integer id) {
		
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
		
	}
	
}
