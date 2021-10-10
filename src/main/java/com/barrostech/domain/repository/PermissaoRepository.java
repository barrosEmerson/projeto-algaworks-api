package com.barrostech.domain.repository;

import java.util.List;

import com.barrostech.domain.model.Permissao;

public interface PermissaoRepository {
	
	List<Permissao> todas();
	Permissao buscar(Long id);
	Permissao salvar(Permissao permissao);
	void remover(Permissao permissao);

}
