package com.welliton.estudonelioalves.estudoSpring;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.welliton.estudonelioalves.estudoSpring.domain.Categoria;
import com.welliton.estudonelioalves.estudoSpring.domain.Cidade;
import com.welliton.estudonelioalves.estudoSpring.domain.Cliente;
import com.welliton.estudonelioalves.estudoSpring.domain.Endereco;
import com.welliton.estudonelioalves.estudoSpring.domain.Estado;
import com.welliton.estudonelioalves.estudoSpring.domain.ItemPedido;
import com.welliton.estudonelioalves.estudoSpring.domain.Pagamento;
import com.welliton.estudonelioalves.estudoSpring.domain.PagamentoComBoleto;
import com.welliton.estudonelioalves.estudoSpring.domain.PagamentoComCartao;
import com.welliton.estudonelioalves.estudoSpring.domain.Pedido;
import com.welliton.estudonelioalves.estudoSpring.domain.Produto;
import com.welliton.estudonelioalves.estudoSpring.domain.enums.EstadoPagamento;
import com.welliton.estudonelioalves.estudoSpring.domain.enums.TipoCliente;
import com.welliton.estudonelioalves.estudoSpring.repository.CategoriaRepository;
import com.welliton.estudonelioalves.estudoSpring.repository.CidadeRepository;
import com.welliton.estudonelioalves.estudoSpring.repository.ClienteRepository;
import com.welliton.estudonelioalves.estudoSpring.repository.EnderecoRepository;
import com.welliton.estudonelioalves.estudoSpring.repository.EstadoRepository;
import com.welliton.estudonelioalves.estudoSpring.repository.ItemPedidoRepository;
import com.welliton.estudonelioalves.estudoSpring.repository.PagamentoRepository;
import com.welliton.estudonelioalves.estudoSpring.repository.PedidoRepository;
import com.welliton.estudonelioalves.estudoSpring.repository.ProdutoRepository;

@SpringBootApplication
public class EstudoSpringApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(EstudoSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 =  new Categoria (null, "Informatica");
		Categoria cat2 =  new Categoria (null, "Escritorio");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 =  new Cidade(null,"Uberlandia", est1);
		Cidade c2 =  new Cidade(null,"São Paulo", est2);
		Cidade c3 =  new Cidade(null,"Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est1.getCidades().addAll(Arrays.asList(c2,c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "369258147", TipoCliente.PESSOAfISICA);
		cli1.getTelefones().addAll(Arrays.asList("123456789","98765431"));
		
		Endereco e1 =  new Endereco(null,"Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
		Endereco e2 =  new Endereco(null,"Avenida Matos", "300", "Apto 303", "Centro", "38220834", cli1, c1);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf =  new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null,sdf.parse("30/09/2017 10:32"),cli1,e1);
		Pedido ped2 = new Pedido(null,sdf.parse("10/10/2017 10:32"),cli1,e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null,EstadoPagamento.QUITADO,ped1,6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2,sdf.parse("20/10/2017 00:00"),null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll( Arrays.asList(pagto1,pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.0);
	    ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 1, 80.0);
	    ItemPedido ip3 = new ItemPedido(ped2, p2, 100.0, 1, 800.0);
	    
	    ped1.getItens().addAll(Arrays.asList(ip1,ip2));
	    ped1.getItens().addAll(Arrays.asList(ip3));
	    
	    p1.getItens().addAll(Arrays.asList(ip1));
	    p2.getItens().addAll(Arrays.asList(ip3));
	    p3.getItens().addAll(Arrays.asList(ip2));
	    
	    itemPedidoRepository.saveAll( Arrays.asList(ip1,ip2,ip3));
		
	}
}
