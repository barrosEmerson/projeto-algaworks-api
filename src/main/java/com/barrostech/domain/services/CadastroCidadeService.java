package com.barrostech.domain.services;

import com.barrostech.domain.exception.CidadeNaoEncontradaException;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
public class CadastroCidadeService {

    public static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser excluída, pois está em uso";
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    @Transactional
    public Cidade salvar(Cidade cidade){
        Long estadoId = cidade.getEstado().getId();
        Estado estado = cadastroEstadoService.buscarOuFalhar(estadoId);

        cidade.setEstado(estado);
        return cidadeRepository.save(cidade);

    }

    @Transactional
    public void excluir(Long cidadeId){
        try{
            cidadeRepository.deleteById(cidadeId);
        }catch (EmptyResultDataAccessException e){
            throw new CidadeNaoEncontradaException( cidadeId);
        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(
                    String.format(MSG_CIDADE_EM_USO, cidadeId)
            );
        }

    }
    public Cidade buscarOuFalhar(Long cidadeId) {
        return cidadeRepository.findById(cidadeId)
                .orElseThrow(() -> new CidadeNaoEncontradaException(cidadeId));
    }
}
