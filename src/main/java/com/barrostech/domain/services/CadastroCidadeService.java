package com.barrostech.domain.services;

import com.barrostech.domain.exception.EntidadeEmUsoException;
import com.barrostech.domain.exception.EntidadeNaoEncontradaException;
import com.barrostech.domain.model.Cidade;
import com.barrostech.domain.model.Estado;
import com.barrostech.domain.repository.CidadeRepository;
import com.barrostech.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CadastroCidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private EstadoRepository estadoRepository;

    public Cidade salvar(Cidade cidade){
        Long estadoId = cidade.getEstado().getId();
        Estado estado = estadoRepository.findById(estadoId).
                orElseThrow(()-> new EntidadeNaoEncontradaException(
                        String.format("Não existe cadastro de estado com o código %d",estadoId)));


            cidade.setEstado(estado);
            return cidadeRepository.save(cidade);

    }

    public void excluir(Long cidadeId){
        try{
            cidadeRepository.deleteById(cidadeId);
        }catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe um cadastro de cidade com o código %d",cidadeId)
            );
        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(
                    String.format("Cidade de código %d não pode ser excluída, pois está em uso",cidadeId)
            );
        }

    }
}
