package com.barrostech.domain.services;

import com.barrostech.domain.exception.EntidadeEmUsoException;
import com.barrostech.domain.exception.EstadoNaoEncontradoException;
import com.barrostech.domain.exception.GrupoNaoEncontradoException;
import com.barrostech.domain.model.Grupo;
import com.barrostech.domain.model.Permissao;
import com.barrostech.domain.repository.GrupoRepository;
import com.barrostech.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroGrupoService {

    private static final String MSG_GRUPO_EM_USO = "O grupo de código %d está em uso.";
    @Autowired
    private GrupoRepository grupoRepository;
    @Autowired
    private CadastroPermissaoService cadastroPermissaoService;

    @Transactional
    public Grupo salvar(Grupo grupo){
        return grupoRepository.save(grupo);
    }

    @Transactional
    public void excluir(Long grupoId){
        try {
            grupoRepository.deleteById(grupoId);
        }catch (EmptyResultDataAccessException e){
            throw new EstadoNaoEncontradoException(grupoId);
        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(
                    String.format(MSG_GRUPO_EM_USO,grupoId)
            );
        }
    }

    public Grupo buscarOuFalhar(Long grupoId){
        return grupoRepository.findById(grupoId).orElseThrow(
                () -> new GrupoNaoEncontradoException(grupoId)
        );
    }

    @Transactional
    public void desassociarPermissao(Long grupoId, Long permissaoId){
        Permissao permissao = cadastroPermissaoService.buscarOuFalhar(permissaoId);
        Grupo grupo = buscarOuFalhar(grupoId);

        grupo.remover(permissao);

    }
    @Transactional
    public void associarPermissao(Long grupoId, Long permissaoId){
        Grupo grupo = buscarOuFalhar(grupoId);
        Permissao permissao = cadastroPermissaoService.buscarOuFalhar(permissaoId);

        grupo.adicionar(permissao);

    }
}
