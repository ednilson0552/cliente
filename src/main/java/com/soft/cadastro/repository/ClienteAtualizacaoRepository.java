package com.soft.cadastro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soft.cadastro.model.atualizacao.ClienteAtualizacao;

@Repository
public interface ClienteAtualizacaoRepository extends JpaRepository<ClienteAtualizacao	, Long> {

}
