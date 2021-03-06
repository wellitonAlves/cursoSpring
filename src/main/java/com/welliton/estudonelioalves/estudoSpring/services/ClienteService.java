package com.welliton.estudonelioalves.estudoSpring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.welliton.estudonelioalves.estudoSpring.domain.Cidade;
import com.welliton.estudonelioalves.estudoSpring.domain.Cliente;
import com.welliton.estudonelioalves.estudoSpring.domain.Endereco;
import com.welliton.estudonelioalves.estudoSpring.domain.enums.Perfil;
import com.welliton.estudonelioalves.estudoSpring.domain.enums.TipoCliente;
import com.welliton.estudonelioalves.estudoSpring.dto.ClienteDTO;
import com.welliton.estudonelioalves.estudoSpring.dto.ClienteNewDTO;
import com.welliton.estudonelioalves.estudoSpring.repository.CidadeRepository;
import com.welliton.estudonelioalves.estudoSpring.repository.ClienteRepository;
import com.welliton.estudonelioalves.estudoSpring.repository.EnderecoRepository;
import com.welliton.estudonelioalves.estudoSpring.resources.exception.AuthorizationException;
import com.welliton.estudonelioalves.estudoSpring.security.UserSS;
import com.welliton.estudonelioalves.estudoSpring.services.exception.DataIntegrityException;
import com.welliton.estudonelioalves.estudoSpring.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;


	public Cliente find(Integer id) {
		
		UserSS user = UserService.authenticated();
		if (user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
		
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(obj);
	}
	
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}

	public void delete(Integer id) {
		
		find(id);
	
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir porque existe entidades relacionadas");
		}
		
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage,String orderBy,String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),
				orderBy);
		return repo.findAll(pageRequest);
		
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
		
		return new Cliente(objDto.getId(),objDto.getNome(), objDto.getEmail(), null, null, null); 
	}
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		
		Cliente cli =  new Cliente(null,objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha())); 
		Optional<Cidade> cid = cidadeRepository.findById(objDto.getCidadeId());
		Endereco end =  new Endereco(null, objDto.getLogradouro(),objDto.getNumero(), objDto.getComplemento(),objDto.getBairro(),objDto.getCep(),cli,cid.get());
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if(objDto.getTelefone2() != null)
			cli.getTelefones().add(objDto.getTelefone2());
		if(objDto.getTelefone3() != null)
			cli.getTelefones().add(objDto.getTelefone3());
		
		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
}
