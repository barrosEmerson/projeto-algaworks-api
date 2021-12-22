package com.barrostech.domain.services;

import com.barrostech.domain.exception.EntidadeEmUsoException;
import com.barrostech.domain.exception.EntidadeNaoEncontradaException;
import com.barrostech.domain.model.Cozinha;
import com.barrostech.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroCozinhaService {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Cozinha salvar(Cozinha cozinha){
        return cozinhaRepository.save(cozinha);
    }

    public void excluir(Long cozinhaId){
        try {
            cozinhaRepository.deleteById(cozinhaId);
        }catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(
                    String.format("Não Existe um cadastro de cozinha com o código %d",cozinhaId))
;        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(
                    String.format("Cozinha não pode ser removida, pois está em uso"));
        }
    }
}
