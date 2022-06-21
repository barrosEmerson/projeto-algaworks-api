package com.barrostech.domain.services;

import com.barrostech.domain.exception.EntidadeEmUsoException;
import com.barrostech.domain.exception.FormaPagamentoNaoEncontradoException;
import com.barrostech.domain.model.FormaPagamento;
import com.barrostech.domain.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CadastroFormaPagamentoService {

    @Autowired
    private FormaPagamentoRepository pagamentoRepository;

    private static final String MSG_FORMA_PAGAMENTO_EM_USO = "Forma de pagamento de código %d não pode ser removida, pois está em uso";

    @Transactional
    public FormaPagamento salvar(FormaPagamento formaPagamento){
        return pagamentoRepository.save(formaPagamento);
    }

    public List<FormaPagamento> listaFormasPagamento(){
        return pagamentoRepository.findAll();
    }

    public FormaPagamento buscarOuFalhar(Long formaPagamentoId){
        return pagamentoRepository.findById(formaPagamentoId).orElseThrow(() -> new FormaPagamentoNaoEncontradoException(formaPagamentoId));
    }

    @Transactional
    public void excluir(Long formaPagamentoId){
        try {
            pagamentoRepository.deleteById(formaPagamentoId);
            pagamentoRepository.flush();
        }catch (EmptyResultDataAccessException e){
            throw new FormaPagamentoNaoEncontradoException(formaPagamentoId);
        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(
                    String.format(MSG_FORMA_PAGAMENTO_EM_USO, formaPagamentoId)
            );
        }
    }
}
