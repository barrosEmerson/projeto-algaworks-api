package com.barrostech.domain.services;

import com.barrostech.domain.exception.EntidadeEmUsoException;
import com.barrostech.domain.exception.EstadoNaoEncontradoException;
import com.barrostech.domain.exception.GrupoNaoEncontrado;
import com.barrostech.domain.model.Grupo;
import com.barrostech.domain.repository.GrupoRepository;
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
                () -> new GrupoNaoEncontrado(grupoId)
        );
    }

}
