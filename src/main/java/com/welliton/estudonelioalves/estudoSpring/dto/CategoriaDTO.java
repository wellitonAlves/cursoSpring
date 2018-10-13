package com.welliton.estudonelioalves.estudoSpring.dto;

import java.io.Serializable;

import com.welliton.estudonelioalves.estudoSpring.domain.Categoria;

public class CategoriaDTO implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	
	public CategoriaDTO() {
		
	}
	
	public CategoriaDTO(Categoria categoria) {
	
		this.id = categoria.getId();
		this.nome =  categoria.getNome();
	}
	
    private Integer id;	
	private String nome;


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
