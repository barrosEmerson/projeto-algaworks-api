package com.barrostech.domain.services;

import com.barrostech.domain.exception.PedidoNaoEncontradoException;
import com.barrostech.domain.model.Pedido;
import com.barrostech.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmissaoPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido buscarOuFalhar(Long pedidoId){
        return pedidoRepository.findById(pedidoId).orElseThrow(()-> new PedidoNaoEncontradoException(pedidoId));
    }
}
