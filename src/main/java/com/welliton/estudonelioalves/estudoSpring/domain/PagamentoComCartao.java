package com.welliton.estudonelioalves.estudoSpring.domain;

import javax.persistence.Entity;

import com.welliton.estudonelioalves.estudoSpring.domain.enums.EstadoPagamento;

@Entity
public class PagamentoComCartao extends Pagamento {
	
	private static final long serialVersionUID = 1L;

	private Integer nueroDeParcelas;

	public PagamentoComCartao() {

	}

	public PagamentoComCartao(Integer id, EstadoPagamento estado, Pedido pedido, Integer numeroDeParcelas) {
		super(id, estado, pedido);
		this.nueroDeParcelas =  numeroDeParcelas;
	}

	public Integer getNueroDeParcelas() {
		return nueroDeParcelas;
	}

	public void setNueroDeParcelas(Integer nueroDeParcelas) {
		this.nueroDeParcelas = nueroDeParcelas;
	}
	
}
