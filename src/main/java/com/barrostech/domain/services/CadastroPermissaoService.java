package com.barrostech.domain.services;

import com.barrostech.domain.exception.PermissaoNaoEncontradoException;
import com.barrostech.domain.model.Permissao;
import com.barrostech.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroPermissaoService {

    @Autowired
    private PermissaoRepository permissaoRepository;

    @Transactional
    public Permissao salvar(Permissao permissao){
        return permissaoRepository.save(permissao);
    }

    public Permissao buscarOuFalhar(Long permissaoId){
        return permissaoRepository.findById(permissaoId).orElseThrow(()-> new PermissaoNaoEncontradoException(permissaoId));
    }
}
