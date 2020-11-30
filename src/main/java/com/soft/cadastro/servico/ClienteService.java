package com.soft.cadastro.servico;

import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soft.cadastro.exception.TrataException;
import com.soft.cadastro.model.Cliente;
import com.soft.cadastro.model.atualizacao.ClienteAtualizacao;
import com.soft.cadastro.repository.ClienteAtualizacaoRepository;
import com.soft.cadastro.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ClienteAtualizacaoRepository clienteAtualizacaoRepository;
	
	public Cliente save(Cliente cliente) {
		Cliente clienteExistente = new Cliente();
		if ((cliente.getEmail() != null) && (cliente.getEmail() != "")) {
			clienteExistente = clienteRepository.findByEmail(cliente.getEmail());

			if (clienteExistente != null && !clienteExistente.equals(cliente)) {
				throw new TrataException("Já existe um cliente cadastrado com este e-mail");
			}
		}
		clienteExistente = clienteRepository.findByCpf(cliente.getCpf());
		if (clienteExistente != null && !clienteExistente.equals(cliente)) {
			throw new TrataException("Já existe um cliente cadastrado com este CPF");
		}
		System.out.println("ENTREI NO clienteRepository.save: "+cliente.getNome());        
		
		cliente.setDataCadastro(new Date(System.currentTimeMillis()));
		return clienteRepository.save(cliente);
	}
	
	public Cliente update(Cliente cliente) {
		Cliente clienteExistente = new Cliente();
		if ((cliente.getEmail() != null) && (cliente.getEmail() != "")) {
			clienteExistente = clienteRepository.findByEmail(cliente.getEmail());

			if (clienteExistente != null && !clienteExistente.equals(cliente)) {
				throw new TrataException("Já existe um cliente cadastrado com este e-mail");
			}
		}

		clienteExistente = clienteRepository.findByCpf(cliente.getCpf());
		if (clienteExistente != null && !clienteExistente.equals(cliente)) {
			throw new TrataException("Já existe um cliente cadastrado com este CPF");
		}
		
		Optional<Cliente> clienteExistente2 = clienteRepository.findById(cliente.getId());
		if (clienteExistente2 != null ) {
			cliente.setDataCadastro( clienteExistente2.get().getDataCadastro());
		}

		ClienteAtualizacao clienteAtualizacao = new ClienteAtualizacao();
		clienteAtualizacao.setCpf(cliente.getCpf());
		clienteAtualizacao.setNome(cliente.getNome());
		clienteAtualizacao.setSexo(cliente.getSexo());
		clienteAtualizacao.setEmail(cliente.getEmail());
		clienteAtualizacao.setDataNascimento(cliente.getDataNascimento());
		clienteAtualizacao.setNaturalidade(cliente.getNaturalidade());
		clienteAtualizacao.setNacionalidade(cliente.getNacionalidade());
		clienteAtualizacao.setdataAtualizacao(new Date(System.currentTimeMillis()));

		clienteAtualizacaoRepository.save(clienteAtualizacao);
		
		
		return clienteRepository.save(cliente);
	}
	
	public void excluir (Long clienteId) {
		clienteRepository.deleteById(clienteId);
	}

}
