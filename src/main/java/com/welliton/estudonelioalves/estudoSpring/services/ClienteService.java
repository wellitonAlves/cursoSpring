package com.welliton.estudonelioalves.estudoSpring.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welliton.estudonelioalves.estudoSpring.domain.Cliente;
import com.welliton.estudonelioalves.estudoSpring.repository.ClienteRepository;
import com.welliton.estudonelioalves.estudoSpring.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;

	public Cliente find(Integer id) {
		
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
		
	}
	
}
